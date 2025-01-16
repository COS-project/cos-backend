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

    public Optional<String> getRefreshToken(TargetUser targetUser) {
        return tokenCache
                .find(targetUser)
                .map(RefreshToken::refreshToken);
    }

    public void banAccessToken(String accessToken) {
        tokenCache.cacheAccessToken(accessToken);
    }

    public boolean isAlreadyLogin(String accessToken) {
        return tokenCache.exists(accessToken);
    }
}
