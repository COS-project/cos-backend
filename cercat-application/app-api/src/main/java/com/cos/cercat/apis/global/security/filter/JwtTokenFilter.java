package com.cos.cercat.apis.global.security.filter;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.apis.global.util.JwtTokenUtil;
import com.cos.cercat.apis.global.util.JwtTokenizer;
import com.cos.cercat.user.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenCacheManager tokenCacheManager;
    private final JwtTokenizer jwtTokenizer;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserReader userReader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = jwtTokenUtil.extractRefreshToken(request)
                .orElse(null);

        if (refreshToken != null) {
            TargetUser targetUser = jwtTokenUtil.extractTargetUser(refreshToken);
            tokenCacheManager.validate(targetUser, refreshToken);
            log.info("userEntity - {} 리프레시 토큰 재발급", targetUser.userId());
            sendAccessTokenAndRefreshToken(targetUser, response);
            throw new CustomException(ErrorCode.REFRESH_TOKEN_REISSUE); //리프레시토큰 재발급 시 401 에러 발생을 방지
        }

        jwtTokenUtil.extractAccessToken(request)//토큰 검증
                .filter(tokenCacheManager::isLoginUser)
                .map(jwtTokenUtil::extractTargetUser)
                .map(userReader::read)
                .ifPresent(this::saveAuthentication);
        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(User user) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("{} 유저 인증 성공", user.getUsername());
    }

    private void sendAccessTokenAndRefreshToken(TargetUser targetUser, HttpServletResponse response){

        String reIssuedAccessToken = jwtTokenizer.generateAccessToken(targetUser, jwtTokenizer.getTokenExpiration());
        String reIssuedRefreshToken = reIssuedRefreshToken(targetUser);

        jwtTokenUtil.setTokenInHeader(response, reIssuedAccessToken, reIssuedRefreshToken);
        log.info("액세스 토큰 및 리프레시 토큰 재발급 완료 - {}", targetUser);
        log.info("재발급 액세스 토큰 - {}", reIssuedAccessToken);
        log.info("재발급 리프레시 토큰 - {}", reIssuedRefreshToken);
    }

    private String reIssuedRefreshToken(TargetUser targetUser) {
        tokenCacheManager.deleteRefreshToken(targetUser);
        String reIssuedRefreshToken = jwtTokenizer.generateRefreshToken(targetUser);

        tokenCacheManager.setRefreshToken(RefreshToken.of(targetUser, reIssuedRefreshToken));
        return reIssuedRefreshToken;
    }


}
