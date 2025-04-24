package com.cos.cercat.apis.global.oauth2;

import com.cos.cercat.domain.user.Provider;
import com.cos.cercat.domain.user.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class OAuthAttributes {

    private final Map<String, Object> attributes;     // OAuth2 반환하는 유저 정보
    private final String nameAttributesKey;
    private final String providerId;
    private final String registrationId;
    private final String name;
    private final String email;
    private final String gender;
    private final String ageRange;
    private final String profileImageUrl;

    private static final String KAKAO_ID = "id";

    @Builder
    public OAuthAttributes(
            Map<String, Object> attributes, String nameAttributesKey,
            String name, String email, String gender, String ageRange,
            String profileImageUrl, String providerId, String registrationId) {
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.providerId = providerId;
        this.registrationId = registrationId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.ageRange = ageRange;
        this.profileImageUrl = profileImageUrl;
    }

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        } else if ("apple".equals(registrationId)) {
            return ofApple(attributes);
        }

        throw new IllegalArgumentException("Unknown social login: " + registrationId);
    }

    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name(String.valueOf(kakaoProfile.get("nickname")))
                .email(String.valueOf(kakaoAccount.get("email")))
                .profileImageUrl(String.valueOf(kakaoProfile.get("profile_image_url")))
                .providerId(String.valueOf(attributes.get(KAKAO_ID)))
                .registrationId("kakao")
                .attributes(attributes)
                .build();
    }

    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofApple(Map<String, Object> attributes) {
        log.info("Apple OAuth attributes: {}", attributes);
        String idToken = (String) attributes.get("id_token");

        Map<String, Object> claims = decodeJwtTokenPayload(idToken);

        String name = null;

        if (attributes.containsKey("user")) {
            Map<String, Object> userMap = (Map<String, Object>) attributes.get("user");
            if (userMap.containsKey("name")) {
                Map<String, Object> nameMap = (Map<String, Object>) userMap.get("name");
                if (nameMap != null) {
                    String firstName = (String) nameMap.get("firstName");
                    String lastName = (String) nameMap.get("lastName");
                    // 성과 이름을 합쳐서 사용
                    name = firstName + lastName;
                }
            }
        }

        String email = (String) claims.get("email");
        String sub = (String) claims.get("sub");


        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .providerId(sub)
                .registrationId("apple")
                .nameAttributesKey("sub") // Apple의 고유 식별자 속성 키는 'sub'
                .attributes(attributes) // 원본 attributes 저장
                .build();
    }

    private static Map<String, Object> decodeJwtTokenPayload(String jwtToken) {
        Map<String, Object> jwtClaims = new java.util.HashMap<>(); // HashMap 사용
        try {
            String[] parts = jwtToken.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = mapper.readValue(decodedString, Map.class);
            jwtClaims.putAll(map);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode id_token payload", e);
        }
        return jwtClaims;
    }

    public UserInfo toUserInfo() {
        return new UserInfo(
                name,
                email,
                Provider.of(registrationId, providerId),
                profileImageUrl
        );
    }

}
