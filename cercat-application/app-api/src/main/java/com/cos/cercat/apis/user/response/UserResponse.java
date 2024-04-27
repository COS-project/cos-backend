package com.cos.cercat.apis.user.response;

import com.cos.cercat.user.User;

public record UserResponse(
        Long userId,
        String nickname,
        String email,
        String profileImage
    ) {

    public static UserResponse from(User user) {

        return new UserResponse(
                user.getId(),
                user.getNickname() != null ?
                        user.getNickname()
                        : user.getUsername(),
                user.getEmail(),
                getProfileImage(user)
        );
    }

    private static String getProfileImage(User user) {
        return  user.getUserProfileImage().getMainProfileImage() != null ?
                user.getUserProfileImage().getMainProfileImage().getImageUrl() :
                user.getUserProfileImage().getKakaoProfileImageUrl();
    }

}
