package com.cos.cercat.domain.user;

public record RefreshToken(
        Long userId,
        String refreshToken
) {
    public static RefreshToken of(UserId userId, String refreshToken) {
        return new RefreshToken(userId.userId(), refreshToken);
    }
}
