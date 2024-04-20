package com.cos.cercat.domain.user;

public record User(
        Long id,
        String nickname,
        String email,
        String username,
        UserProfileImage userProfileImage,
        Role userRole
) {
}
