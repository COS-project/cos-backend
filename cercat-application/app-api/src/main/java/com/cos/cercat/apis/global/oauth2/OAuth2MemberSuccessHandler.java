package com.cos.cercat.apis.global.oauth2;

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

import static com.cos.cercat.apis.global.util.JwtTokenizer.*;
import static com.cos.cercat.apis.global.util.JwtTokenizer.generateRefreshToken;

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

    private final UserReader userReader;
    private final UserCacheManager userCacheManager;
    private final TokenManager tokenManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();
        TargetUser targetUser = TargetUser.from(oAuth2User.getUserId());
        User user = userReader.read(targetUser);
        userCacheManager.cache(user); // 유저 캐싱
        String accessToken = generateAccessToken(targetUser);
        String refreshToken = generateRefreshToken(targetUser);
        tokenManager.saveRefreshToken(RefreshToken.of(targetUser, refreshToken));
        redirect(response, accessToken, refreshToken, user);
    }

    private void redirect(HttpServletResponse response, String accessToken, String refreshToken, User user) throws IOException {
        if (user.isGuest()) {
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
