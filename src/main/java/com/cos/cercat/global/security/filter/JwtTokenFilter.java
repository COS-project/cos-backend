package com.cos.cercat.global.security.filter;

import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.global.util.JwtTokenUtil;
import com.cos.cercat.global.util.JwtTokenizer;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.cache.RefreshToken;
import com.cos.cercat.user.cache.TokenCacheRepository;
import com.cos.cercat.user.dto.UserDTO;
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
import java.util.HashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenCacheRepository tokenCacheRepository;
    private final JwtTokenizer jwtTokenizer;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = jwtTokenUtil.extractRefreshToken(request)
                .orElse(null);

        if (refreshToken != null) {
            log.info("리프레시 토큰이 헤더에 존재 - {}", refreshToken);

            String email = jwtTokenUtil.extractEmailFromRefreshToken(refreshToken);

            RefreshToken validRefreshToken = tokenCacheRepository.getRefreshToken(email)
                    .filter(existingToken -> existingToken.getRefreshToken().equals(refreshToken))
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN));

            log.info("리프레시 토큰 유효, 재발급 로직 실행");
            sendAccessTokenAndRefreshToken(validRefreshToken.getEmail(), response);
            throw new CustomException(ErrorCode.REFRESH_TOKEN_REISSUE); //리프레시토큰 재발급 시 401 에러 발생을 방지
        }

        jwtTokenUtil.extractAccessToken(request)//토큰 검증
                .map(jwtTokenUtil::extractEmail)
                .filter(userService::isLoginUser)
                .map(userService::findByEmail)
                .ifPresent(this::saveAuthentication);

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(UserDTO user) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("{} 유저 인증 성공", user.getNickname());
    }

    private void sendAccessTokenAndRefreshToken(String email, HttpServletResponse response){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        String reIssuedAccessToken = jwtTokenizer.generateAccessToken(claims, email, jwtTokenizer.getTokenExpiration());
        String reIssuedRefreshToken = reIssuedRefreshToken(email);

        jwtTokenUtil.sendAccessAndRefreshToken(response, reIssuedAccessToken, reIssuedRefreshToken);
        log.info("액세스 토큰 및 리프레시 토큰 재발급 완료 - {}", email);
        log.info("재발급 액세스 토큰 - {}", reIssuedAccessToken);
        log.info("재발급 리프레시 토큰 - {}", reIssuedRefreshToken);
    }

    private String reIssuedRefreshToken(String email) {
        tokenCacheRepository.deleteRefreshToken(email);
        String reIssuedRefreshToken = jwtTokenizer.generateRefreshToken(email);
        
        tokenCacheRepository.setRefreshToken(RefreshToken.of(email, reIssuedRefreshToken));
        return reIssuedRefreshToken;
    }


}
