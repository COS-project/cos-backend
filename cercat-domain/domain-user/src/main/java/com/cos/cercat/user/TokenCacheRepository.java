package com.cos.cercat.user;


import java.util.Optional;

public interface TokenCacheRepository {
    void setRefreshToken(RefreshToken refreshToken);

    Optional<RefreshToken> getRefreshToken(TargetUser targetUser);

    void deleteRefreshToken(TargetUser targetUser);
}
