package com.cos.cercat.user;

import com.cos.cercat.common.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String nickname;
    private String email;
    private String username;
    private UserProfileImage userProfileImage;
    private Role userRole;

    public void update(String nickname, Image image) {
        this.nickname = nickname;
        this.userProfileImage = UserProfileImage.of(image, userProfileImage.getKakaoProfileImageUrl());
    }

    public void oauthUpdate(String email, String kakaoProfileImageUrl) {
        this.email = email;
        this.userProfileImage = UserProfileImage.of(userProfileImage.getMainProfileImage(), kakaoProfileImageUrl);
    }
}