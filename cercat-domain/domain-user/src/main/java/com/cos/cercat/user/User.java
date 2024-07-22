package com.cos.cercat.user;

import com.cos.cercat.common.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    public void update(String nickname, Image image) {
        this.nickname = nickname;
        this.userProfileImage.updateProfileImage(image, userProfileImage.getKakaoProfileImageUrl());
    }

    public void updateRole() {
        this.userRole = Role.ROLE_USER;
    }

    public void oauthUpdate(UserInfo userInfo) {
        this.username = userInfo.username();
        this.email = userInfo.email();
        this.userProfileImage.updateProfileImage(null, userInfo.kakaoProfileImage());
    }

    public boolean isGuest() {
        return userRole == Role.ROLE_GUEST;
    }
}