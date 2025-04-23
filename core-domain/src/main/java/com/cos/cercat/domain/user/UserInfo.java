package com.cos.cercat.domain.user;

public record UserInfo(
        String username,
        String email,
        Provider provider,
        String kakaoProfileImage
) {
}
