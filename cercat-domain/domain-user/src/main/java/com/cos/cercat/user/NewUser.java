package com.cos.cercat.user;

public record NewUser(
        String username,
        String email,
        String kakaoProfileImage,
        Role role
) {
}
