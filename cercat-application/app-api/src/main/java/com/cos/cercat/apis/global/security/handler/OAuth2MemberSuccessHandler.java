package com.cos.cercat.apis.global.security.handler;

import com.cos.cercat.apis.global.security.OAuth2CustomUser;
import com.cos.cercat.apis.global.util.JwtTokenizer;
import com.cos.cercat.user.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${front.host}")
    private String FRONT_HOST;

    @Value("${front.port}")
    private String FRONT_PORT;

    @Value("${front.home-path}")
    private String HOME_PATH;

    @Value("${front.sign-up-path}")
    private String SIGN_UP_PATH;

    private final JwtTokenizer jwtTokenizer;
    private final UserReader userReader;
    private final UserCacheManger userCacheManger;
    private final TokenCacheManager tokenCacheManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();

        List<String> roles = oAuth2User.getAuthorities().stream()
                .map(String::valueOf)
                .toList(); // Authorities에서 문자열로 권한 추출

        TargetUser targetUser = TargetUser.from(oAuth2User.getUserId());
        redirect(response, targetUser, roles);
    }

    private void redirect(HttpServletResponse response, TargetUser targetUser, List<String> roles) throws IOException {
        String accessToken = delegateAccessToken(targetUser);
        String refreshToken = jwtTokenizer.generateRefreshToken(targetUser);
        User user = userReader.read(targetUser);

        tokenCacheManager.getRefreshToken(targetUser).ifPresent(token -> // 이미 로그인 한 유저가 또 로그인했을 경우 리프레시토큰 갱신.
                tokenCacheManager.deleteRefreshToken(targetUser));

        tokenCacheManager.setRefreshToken(RefreshToken.of(targetUser, refreshToken));
        userCacheManger.append(user); // 로그인 시 유저 캐싱

        log.info("로그인 성공 accessToken 발급  - {}", accessToken);
        log.info("로그인 성공 refreshToken 발급 - {}", refreshToken);


        if (roles.contains("ROLE_GUEST")) { // 첫 소셜로그인 하는 유저일경우 추가 회원정보를 입력하는 폼으로 리다이렉트
            response.sendRedirect(getURIString(accessToken, refreshToken, SIGN_UP_PATH));
            log.info("신규 유저 {} 리다이렉트", user);
            return;
        }

        log.info("기존 유저 {} 리다이렉트", user);
        response.sendRedirect(getURIString(accessToken, refreshToken, HOME_PATH));
    }

    // Access Token 생성
    private String delegateAccessToken(TargetUser targetUser) {
        return jwtTokenizer.generateAccessToken(targetUser, jwtTokenizer.getTokenExpiration());
    }

    private String getURIString(String accessToken, String refreshToken, String path) {
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("accessToken", "Bearer " + accessToken);
        queryParam.add("refreshToken", "Bearer " + refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(FRONT_HOST)
                .port(FRONT_PORT)
                .path(path)
                .queryParams(queryParam)
                .build()
                .toUri()
                .toString();
    }
}
