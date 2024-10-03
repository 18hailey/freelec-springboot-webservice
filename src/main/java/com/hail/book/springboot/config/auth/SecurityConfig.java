package com.hail.book.springboot.config.auth;

import com.hail.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들 활성화시켜줌
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                                    AntPathRequestMatcher.antMatcher("/"),
                                    AntPathRequestMatcher.antMatcher("/css/**"),
                                    AntPathRequestMatcher.antMatcher("/images/**"),
                                    AntPathRequestMatcher.antMatcher("/js/**"),
                                    AntPathRequestMatcher.antMatcher("/h2-console/**"),
                                    AntPathRequestMatcher.antMatcher("/auth/success"),
                                    AntPathRequestMatcher.antMatcher("/oauth2/**"),
                                    AntPathRequestMatcher.antMatcher("/login/**"),
                                    AntPathRequestMatcher.antMatcher("/error")
                            ).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/**")).hasRole(Role.USER.name())
                            .anyRequest().authenticated();

                })
                .logout((logoutConfig) -> // 로그아웃 기능에 대한 여러 설정의 진입점
                        logoutConfig.logoutSuccessUrl("/"))

                // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .oauth2Login(oauth ->
                                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                                oauth.userInfoEndpoint(c ->
                                        c.userService(customOAuth2UserService))
                        // (아래) 소설 로그인 성공 시 후속 조치 진행할 UserService 인터페이스의 구현체
                        // (아래) 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
                );

        return http.build();
    }
}
