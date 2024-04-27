package com.cos.cercat.user;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenCacheManager {

    private final TokenCacheRepository tokenCacheRepository;
    private final BanTokenRepository banTokenRepository;

    public void setRefreshToken(RefreshToken refreshToken) {
        tokenCacheRepository.setRefreshToken(refreshToken);
    }

    public Optional<RefreshToken> getRefreshToken(TargetUser targetUser) {
        return tokenCacheRepository.getRefreshToken(targetUser);
    }

    public void deleteRefreshToken(TargetUser targetUser) {
        tokenCacheRepository.deleteRefreshToken(targetUser);
    }

    public void ban(String accessToken) {
        banTokenRepository.ban(accessToken);
    }

    public boolean isLoginUser(String accessToken) {
        return banTokenRepository.isLoginUser(accessToken);
    }

    public void validate(TargetUser targetUser, String refreshToken) {
        RefreshToken validRefreshToken = getRefreshToken(targetUser).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN));
        if (!validRefreshToken.refreshToken().equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
