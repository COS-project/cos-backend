package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenManager {

    private final TokenCache tokenCache;

    public void saveRefreshToken(RefreshToken refreshToken) {
        tokenCache.cacheRefreshToken(refreshToken);
    }

    public Optional<String> getRefreshToken(UserId userId) {
        return tokenCache
                .find(userId)
                .map(RefreshToken::refreshToken);
    }

    public void banAccessToken(String accessToken) {
        tokenCache.cacheAccessToken(accessToken);
    }

    public boolean isAlreadyLogin(String accessToken) {
        return tokenCache.exists(accessToken);
    }
}
