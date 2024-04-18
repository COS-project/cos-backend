package com.cos.cercat.domain.user;

public record User(
        Long userId,
        String nickname,
        String email,
        String username,
        UserProfileImage userProfileImage,
        Role userRole
) {
}
