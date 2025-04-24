package com.cos.cercat.apis.global.oauth2;

import static com.cos.cercat.security.JwtTokenizer.generateAccessToken;
import static com.cos.cercat.security.JwtTokenizer.generateRefreshToken;

import com.cos.cercat.apis.global.oauth2.apple.AppleUserInfo;
import com.cos.cercat.domain.user.*;

import com.cos.cercat.domain.user.UserReader;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${front.scheme}")
    private String FRONT_SCHEME;

    @Value("${front.host}")
    private String FRONT_HOST;

    @Value("${front.home-path}")
    private String HOME_PATH;

    @Value("${front.sign-up-path}")
    private String SIGN_UP_PATH;

    private final UserReader userReader;
    private final UserCacheManager userCacheManager;
    private final TokenManager tokenManager;
    private final UserUpdater userUpdater;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String userInfo = request.getParameter("user");
        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();
        UserId userId = UserId.from(oAuth2User.getUserId());
        User user = userReader.read(userId);
        if (isFirstAppleUser(userInfo)) {
            AppleUserInfo appleUserInfo = objectMapper.readValue(userInfo, AppleUserInfo.class);
            user = user.update(appleUserInfo.getFullName(), appleUserInfo.email());
            userUpdater.update(user);
        }
        userCacheManager.cache(user); // 유저 캐싱
        String accessToken = generateAccessToken(userId);
        String refreshToken = generateRefreshToken(userId);
        tokenManager.saveRefreshToken(RefreshToken.of(userId, refreshToken));
        redirect(response, accessToken, refreshToken, user);
    }

    private static boolean isFirstAppleUser(String firstAppleUser) {
        return firstAppleUser != null;
    }

    private void redirect(HttpServletResponse response, String accessToken, String refreshToken, User user)
            throws IOException {
        if (user.checkGuest()) {
            response.sendRedirect(getURIString(accessToken, refreshToken, SIGN_UP_PATH));
            return;
        }
        response.sendRedirect(getURIString(accessToken, refreshToken, HOME_PATH));
    }

    private String getURIString(String accessToken, String refreshToken, String path) {
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("accessToken", "Bearer " + accessToken);
        queryParam.add("refreshToken", "Bearer " + refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme(FRONT_SCHEME)
                .host(FRONT_HOST)
                .path(path)
                .queryParams(queryParam)
                .build()
                .toUri()
                .toString();
    }
}
