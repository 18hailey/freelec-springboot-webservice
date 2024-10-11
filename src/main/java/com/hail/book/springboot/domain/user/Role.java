package com.hail.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 두 가지 상수 정의
    // 각각의 상수에 key, title 필드 부여
    // 스프링 시큐리티에서는 권한 코드에 항상 'ROLE_'이 앞에 있어야만 한다

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용지");

    private final String key;
    private final String title;
}
