package com.cos.cercat.domain.user;

public record RefreshToken(
        Long userId,
        String refreshToken
) {
    public static RefreshToken of(TargetUser targetUser, String refreshToken) {
        return new RefreshToken(targetUser.userId(), refreshToken);
    }
}
