package com.cos.cercat.domain.user;

public class UserFixture {

  public static User createUser() {
    return User.builder()
        .nickname("nickname")
        .email("email")
        .username("username")
        .userRole(Role.ROLE_USER)
        .userProfileImage(new UserProfileImage("test.jpg"))
        .build();
  }

  public static UserInfo createUserInfo() {
    return new UserInfo(
        "username",
        "email",
        "test.jpg"
    );
  }


}
