package com.cos.cercat.apis.global.oauth2;

import com.cos.cercat.user.UserInfo;
import com.cos.cercat.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class OAuthAttributes {

    private Map<String, Object> attributes;     // OAuth2 반환하는 유저 정보
    private String nameAttributesKey;
    private String name;
    private String email;
    private String gender;
    private String ageRange;
    private String profileImageUrl;

    private static final String KAKAO_ID = "id";

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributesKey,
                           String name, String email, String gender, String ageRange, String profileImageUrl) {
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.ageRange = ageRange;
        this.profileImageUrl = profileImageUrl;
    }

    public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
        if ("kakao".equals(socialName)) {
            return ofKakao(attributes);
        }

        throw new IllegalArgumentException("Unknown social login");
    }

    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name(String.valueOf(kakaoProfile.get("nickname")))
                .email(String.valueOf(kakaoAccount.get("email")))
                .profileImageUrl(String.valueOf(kakaoProfile.get("profile_image_url")))
                .nameAttributesKey(KAKAO_ID)
                .attributes(attributes)
                .build();
    }

    public UserInfo toUserInfo() {
        return new UserInfo(
                name,
                email,
                profileImageUrl
        );
    }

}
