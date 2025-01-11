package com.cos.cercat.domain.user;

import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOpTokenRepository implements TokenRepository {

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {

    }

    @Override
    public Optional<RefreshToken> getRefreshToken(TargetUser targetUser) {
        return Optional.empty();
    }

    @Override
    public void ban(String banToken) {

    }

    @Override
    public boolean isAlreadyLogin(String token) {
        return false;
    }
}
