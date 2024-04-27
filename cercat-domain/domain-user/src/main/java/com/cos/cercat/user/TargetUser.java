package com.cos.cercat.user;

public record TargetUser(
        Long userId
) {

    public static TargetUser from(Long userId) {
        return new TargetUser(userId);
    }
}
