package com.cos.cercat.domain.user;

public record UserProfileImage(
        String mainProfileImageUrl,
        String kakaoProfileImageUrl
) {

    public static UserProfileImage of(String mainProfileImageUrl, String kakaoProfileImageUrl) {
        return new UserProfileImage(mainProfileImageUrl, kakaoProfileImageUrl);
    }
}
