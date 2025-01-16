package com.cos.cercat.domain.user;


import java.util.Optional;

public interface TokenCache {
    void cacheRefreshToken(RefreshToken refreshToken);

    void cacheAccessToken(String accessToken);

    Optional<RefreshToken> find(TargetUser targetUser);

    boolean exists(String token);
}
