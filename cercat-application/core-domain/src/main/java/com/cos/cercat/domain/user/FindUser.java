package com.cos.cercat.domain.user;

public record FindUser(
        Long userId
) {

    public static FindUser from(Long userId) {
        return new FindUser(userId);
    }
}
