package com.cos.cercat.dto;


import com.cos.cercat.domain.UserEntity;

public record AlarmArg(
        UserEntity fromUserEntity,
        Long postId
) {
    public static AlarmArg of(UserEntity fromUserEntity, Long postId) {
        return new AlarmArg(
                fromUserEntity,
                postId
        );
    }
}
