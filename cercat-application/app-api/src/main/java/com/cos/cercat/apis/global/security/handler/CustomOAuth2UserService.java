package com.cos.cercat.apis.global.security.handler;

import com.cos.cercat.apis.global.security.OAuth2CustomUser;
import com.cos.cercat.apis.global.security.OAuthAttributes;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserReader userReader;
    private final UserAppender userAppender;

    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private final List<GrantedAuthority> GUEST_ROLES = AuthorityUtils.createAuthorityList("ROLE_GUEST");
    private final UserUpdater userUpdater;


    @Override // 로그인 로직 담당
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService 진입 - 소셜 로그인 로직");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);  // 어떤 플랫폼의 유저정보도 담을수있도록 추상화한 클래스 OAuth2 정보를 가져옵니다.

        Map<String, Object> originAttributes = oAuth2User.getAttributes();  // OAuth2User의 attribute

        // OAuth2 서비스 id (google, kakao, naver)
        // 소셜 정보를 가져옵니다. oauth2/atholization/{kakao} 이부분에 해당
        String registrationId = userRequest.getClientRegistration().getRegistrationId();


        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);
        assert attributes != null;
        User user = saveOrUpdate(attributes);
        log.info("소셜 로그인 유저 정보 또는 업데이트");
        List<GrantedAuthority> authorities = createAuthorities(TargetUser.from(user.getId())); //Role 정보 가져오기

        return new OAuth2CustomUser(registrationId, originAttributes, authorities, user.getEmail(), user.getId());
    }

    private User saveOrUpdate(OAuthAttributes authAttributes) {
        try {
            User user = userReader.readBy(authAttributes.getEmail());
            user.oauthUpdate(authAttributes.getEmail(), authAttributes.getProfileImageUrl());
            userUpdater.update(user);
            return user;
        } catch (CustomException e) { // 회원가입이 안되어있을때
            NewUser newUser = authAttributes.toNewUser();
            return userAppender.append(newUser);
        }
    }

    private List<GrantedAuthority> createAuthorities(TargetUser targetUser) {

        User user = userReader.read(targetUser);
        if (user.getUserRole().equals(Role.ROLE_USER)) {
            return USER_ROLES;
        } else {
            return GUEST_ROLES;
        }

    }
}
