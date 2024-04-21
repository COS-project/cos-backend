package com.cos.cercat.domain.user;

import org.springframework.util.StringUtils;

public record User(
        Long id,
        String nickname,
        String email,
        String username,
        UserProfileImage userProfileImage,
        Role userRole
) {

    public String getUserProfileImage() {
        return (StringUtils.hasText(userProfileImage().mainProfileImageUrl()) ?
                userProfileImage().mainProfileImageUrl() :
                userProfileImage().kakaoProfileImageUrl());
    }
}
