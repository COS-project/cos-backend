package com.cos.cercat.global.security.handler;

import com.cos.cercat.global.util.JwtTokenizer;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.cache.UserCacheRepository;
import com.cos.cercat.user.cache.RefreshToken;
import com.cos.cercat.user.cache.TokenCacheRepository;
import com.cos.cercat.user.domain.OAuth2CustomUser;
import com.cos.cercat.user.dto.UserDTO;
import jakarta.servlet.ServletException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final UserService userService;
    private final TokenCacheRepository tokenCacheRepository;
    private final UserCacheRepository userCacheRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();

        List<String> roles = oAuth2User.getAuthorities().stream()
                .map(String::valueOf)
                .toList(); // Authorities에서 문자열로 권한 추출

        String email = oAuth2User.getEmail();

        redirect(response, email, roles);

    }

    private void redirect(HttpServletResponse response, String email, List<String> roles) throws IOException {
        String accessToken = delegateAccessToken(email, roles);  // Access Token 생성// Refresh Token 생성
        String refreshToken = jwtTokenizer.generateRefreshToken(email);
        UserDTO user = userService.findByEmail(email);

        tokenCacheRepository.getRefreshToken(email) // 이미 로그인 한 유저가 또 로그인했을 경우 리프레시토큰 갱신
                        .ifPresent(token ->
                                tokenCacheRepository.deleteRefreshToken(email)
                        );

        tokenCacheRepository.setRefreshToken(RefreshToken.of(user.getEmail(), refreshToken));
        userCacheRepository.setUser(user); // 로그인 시 유저 캐싱

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
    private String delegateAccessToken(String email, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", authorities);

        return jwtTokenizer.generateAccessToken(claims, email, jwtTokenizer.getTokenExpiration());
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
