package com.cos.cercat.domain.user;


import java.util.Optional;

public interface TokenCache {
    void cacheRefreshToken(RefreshToken refreshToken);

    void cacheAccessToken(String accessToken);

    Optional<RefreshToken> find(UserId userId);

    boolean exists(String token);
}
