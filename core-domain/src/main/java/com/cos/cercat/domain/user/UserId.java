package com.cos.cercat.domain.user;

public record UserId(
        Long userId
) {

    public static UserId from(Long userId) {
        return new UserId(userId);
    }
}
