package com.cos.cercat.apis.global.oauth2;

import com.cos.cercat.domain.user.exception.UserNotFoundException;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserAppender;
import com.cos.cercat.domain.user.UserReader;
import com.cos.cercat.domain.user.UserUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserReader userReader;
    private final UserAppender userAppender;
    private final UserUpdater userUpdater;


    @Override // 로그인 로직 담당
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(
                userRequest);  // 어떤 플랫폼의 유저정보도 담을수있도록 추상화한 클래스 OAuth2 정보를 가져옵니다.

        Map<String, Object> originAttributes = oAuth2User.getAttributes();  // OAuth2User의 attribute

        // OAuth2 서비스 questionId (google, kakao, naver)
        // 소셜 정보를 가져옵니다. oauth2/atholization/{kakao} 이부분에 해당
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);
        User user = saveOrUpdate(attributes);
        return new OAuth2CustomUser(registrationId, originAttributes, user.getUserRole(),
                user.getEmail(), user.getId());
    }

    private User saveOrUpdate(OAuthAttributes authAttributes) {
        try {
            User user = userReader.readBy(authAttributes.getEmail());
            user.oauthUpdate(authAttributes.toUserInfo());
            return userUpdater.update(user);
        } catch (UserNotFoundException e) { // 회원가입이 안되어있을때
            return userAppender.append(authAttributes.toUserInfo());
        }
    }
}
