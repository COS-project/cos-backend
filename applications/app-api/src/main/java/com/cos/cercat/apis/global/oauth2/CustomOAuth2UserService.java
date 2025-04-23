package com.cos.cercat.apis.global.oauth2;

import com.cos.cercat.domain.user.Provider;
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


    @Override // 소셜 로그인 로직 담당
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes oAuthAttributes;

        if (isApple(registrationId)) {
            oAuthAttributes = OAuthAttributes.of(registrationId, userRequest.getAdditionalParameters());
        } else {
            // 소셜 정보를 가져옵니다. oauth2/atholization/{kakao} 이부분에 해당
            Map<String, Object> originAttributes = service.loadUser(userRequest).getAttributes();
            oAuthAttributes = OAuthAttributes.of(registrationId, originAttributes);
        }

        User user = saveOrUpdate(oAuthAttributes);

        return new OAuth2CustomUser(
                registrationId,
                oAuthAttributes.getAttributes(),
                user.getUserRole(),
                user.getEmail(),
                user.getId()
        );
    }

    private User saveOrUpdate(OAuthAttributes authAttributes) {
        try {
            Provider provider = Provider.of(authAttributes.getRegistrationId(),
                    authAttributes.getProviderId());
            User user = userReader.read(provider);
            user.oauthUpdate(authAttributes.toUserInfo());
            return userUpdater.update(user);
        } catch (UserNotFoundException e) { // 회원가입이 안되어있을때
            return userAppender.append(authAttributes.toUserInfo());
        }
    }

    private boolean isApple(String registrationId) {
        return registrationId.equals("apple");
    }
}
