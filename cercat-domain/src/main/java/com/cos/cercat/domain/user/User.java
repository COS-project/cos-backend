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
        return (StringUtils.hasText(userProfileImage.getMainProfileImage().getImageUrl()) ?
                userProfileImage().getMainProfileImage().getImageUrl() :
                userProfileImage().getKakaoProfileImageUrl());
    }
}
