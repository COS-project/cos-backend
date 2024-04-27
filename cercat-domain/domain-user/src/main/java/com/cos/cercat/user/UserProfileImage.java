package com.cos.cercat.user;

import com.cos.cercat.common.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileImage {
    private Image mainProfileImage;
    private String kakaoProfileImageUrl;

    // 문자열 인자를 받는 생성자
    public UserProfileImage(String kakaoProfileImageUrl) {
        this.kakaoProfileImageUrl = kakaoProfileImageUrl;
    }

    public static UserProfileImage of(Image mainProfileImage, String kakaoProfileImageUrl) {
        return new UserProfileImage(mainProfileImage, kakaoProfileImageUrl);
    }
}
