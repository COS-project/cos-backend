package com.cos.cercat.domain.user;

import com.cos.cercat.domain.common.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  private Long id;
  private String nickname;
  private String email;
  private String username;
  private UserProfileImage userProfileImage;
  private Role userRole;
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

  public boolean checkGuest() {
    return userRole == Role.ROLE_GUEST;
  }
}
