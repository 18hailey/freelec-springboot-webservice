package com.hail.book.springboot.config.auth;

import com.hail.book.springboot.config.auth.dto.OAuthAttributes;
import com.hail.book.springboot.config.auth.dto.SessionUser;
import com.hail.book.springboot.domain.user.User;
import com.hail.book.springboot.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    /**
     * 구글 로그인 "이후" 가져온 사용자의 정보(email, name, picture..)들을 기반으로
     * 가입 및 정보 수정, 세션 저장 등의 기능 지원
     */

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스 구분하는 코드(구글 혹은 네이버)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // 로그인 진행 시 키가 되는 필드값 (PK)
        System.out.println("userNameAttributeName = " + userNameAttributeName);
        OAuthAttributes attributes = OAuthAttributes // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
                .of(registrationId, userNameAttributeName,
                        oAuth2User.getAttributes());
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user)); // 세션에 사용자 정보를 저장하기 위한 DTO
        return new DefaultOAuth2User(
                Collections.singleton((new SimpleGrantedAuthority(user.getRoleKey()))),
                        attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())) // Optional에 값이 존재할 때 실행되는 메서드
                .orElse(attributes.toEntity()); // Optional에 값이 없을 때 실행되는 메서드
        return userRepository.save(user);
    }
}
