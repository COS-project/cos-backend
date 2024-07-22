package com.cos.cercat.user;


import java.util.Optional;

public interface TokenRepository {
    void saveRefreshToken(RefreshToken refreshToken);

    Optional<RefreshToken> getRefreshToken(TargetUser targetUser);

    void ban(String banToken);

    boolean isAlreadyLogin(String token);
}
