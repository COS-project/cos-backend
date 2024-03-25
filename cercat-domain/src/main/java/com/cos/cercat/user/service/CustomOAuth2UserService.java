package com.cos.cercat.user.service;

import com.cos.cercat.exception.CustomException;
import com.cos.cercat.exception.ErrorCode;
import com.cos.cercat.user.domain.OAuth2CustomUser;
import com.cos.cercat.user.domain.OAuthAttributes;
import com.cos.cercat.user.domain.Role;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.repository.UserRepository;
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

    private final UserRepository userRepository;

    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private final List<GrantedAuthority> GUEST_ROLES = AuthorityUtils.createAuthorityList("ROLE_GUEST");


    @Override // 로그인 로직 담당
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService 진입 - 소셜 로그인 로직");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);  // 어떤 플랫폼의 유저정보도 담을수있도록 추상화한 클래스 OAuth2 정보를 가져옵니다.

        Map<String, Object> originAttributes = oAuth2User.getAttributes();  // OAuth2User의 attribute

        // OAuth2 서비스 id (google, kakao, naver)
        // 소셜 정보를 가져옵니다. oauth2/atholization/{kakao} 이부분에 해당
        String registrationId = userRequest.getClientRegistration().getRegistrationId();


        // OAuthAttributes: OAuth2User의 attribute를 서비스 유형에 맞게 담아줄 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);
        assert attributes != null;
        User user = saveOrUpdate(attributes);
        log.info("소셜 로그인 유저 정보 또는 업데이트");
        String email = user.getEmail();
        List<GrantedAuthority> authorities = createAuthorities(email); //Role 정보 가져오기

        return new OAuth2CustomUser(registrationId, originAttributes, authorities, email);
    }

    private User saveOrUpdate(OAuthAttributes authAttributes) {
        User member = userRepository.findByEmail(authAttributes.getEmail())
                .map(entity -> entity.oauthUpdate(authAttributes.getEmail(), authAttributes.getProfileImageUrl())) //이메일 및 프로필이미지 업데이트
                .orElse(authAttributes.toEntity());

        return userRepository.save(member);
    }

    private List<GrantedAuthority> createAuthorities(String email) {

        return userRepository.findByEmail(email)
                .map(user -> {
                    if (user.getRole().equals(Role.ROLE_GUEST)) {
                        return GUEST_ROLES;
                    }
                    return USER_ROLES;
                }).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    }
}
