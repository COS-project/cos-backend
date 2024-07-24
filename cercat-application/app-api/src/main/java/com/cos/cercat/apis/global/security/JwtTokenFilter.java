package com.cos.cercat.apis.global.security;

import com.cos.cercat.apis.global.security.exception.InvalidTokenException;
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
import java.util.Optional;

import static com.cos.cercat.apis.global.util.JwtTokenizer.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;
    private final UserReader userReader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> refreshToken = extractRefreshToken(request);

        if (refreshToken.isPresent()) {
            TargetUser targetUser = extractTargetUser(refreshToken.get());
            validateRefreshToken(targetUser, refreshToken.get());
            reIssueToken(targetUser, response);
            log.info("userEntity - {} 리프레시 토큰 재발급", targetUser.userId());
            return;
        }

        extractAccessToken(request)
                .filter(tokenManager::isAlreadyLogin)
                .map(JwtTokenizer::extractTargetUser)
                .map(userReader::read)
                .ifPresent(this::saveAuthentication);

        filterChain.doFilter(request, response);
    }

    private void validateRefreshToken(TargetUser targetUser, String givenRefreshToken) {
        tokenManager.getRefreshToken(targetUser)
                .filter(existRefreshToken -> existRefreshToken.equals(givenRefreshToken))
                .orElseThrow(() -> InvalidTokenException.EXCEPTION);
    }

    private void reIssueToken(TargetUser targetUser, HttpServletResponse response) {
        String reIssuedAccessToken = generateAccessToken(targetUser);
        String reIssuedRefreshToken = generateRefreshToken(targetUser);
        tokenManager.saveRefreshToken(RefreshToken.of(targetUser, reIssuedRefreshToken));
        setInHeader(response, reIssuedAccessToken, reIssuedRefreshToken);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    private void saveAuthentication(User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
