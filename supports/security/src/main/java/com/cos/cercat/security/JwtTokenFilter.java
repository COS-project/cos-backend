package com.cos.cercat.security;

import com.cos.cercat.domain.user.UserReader;
import com.cos.cercat.security.exception.TokenParseFailedException;
import com.cos.cercat.domain.user.*;

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


@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;
    private final UserReader userReader;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Optional<String> refreshToken = JwtTokenizer.extractRefreshToken(request);

            if (refreshToken.isPresent()) {
                UserId userId = JwtTokenizer.extractTargetUser(refreshToken.get());
                validateRefreshToken(userId, refreshToken.get());
                reIssueToken(userId, response);
                log.info("userEntity - {} 리프레시 토큰 재발급", userId.userId());
                return;
            }

            JwtTokenizer.extractAccessToken(request)
                    .filter(tokenManager::isAlreadyLogin)
                    .map(JwtTokenizer::extractTargetUser)
                    .map(userReader::read)
                    .ifPresentOrElse(
                            this::saveAuthentication,
                            () -> {throw TokenParseFailedException.EXCEPTION;}
                    );

        } catch (Exception e) {
            log.error(e.getMessage());
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private void validateRefreshToken(UserId userId, String givenRefreshToken) {
        tokenManager.getRefreshToken(userId)
                .filter(existRefreshToken -> existRefreshToken.equals(givenRefreshToken))
                .orElseThrow(() -> TokenParseFailedException.EXCEPTION);
    }

    private void reIssueToken(UserId userId, HttpServletResponse response) {
        String reIssuedAccessToken = JwtTokenizer.generateAccessToken(userId);
        String reIssuedRefreshToken = JwtTokenizer.generateRefreshToken(userId);
        tokenManager.saveRefreshToken(RefreshToken.of(userId, reIssuedRefreshToken));
        JwtTokenizer.setInHeader(response, reIssuedAccessToken, reIssuedRefreshToken);
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
