package com.cos.cercat.alarm.dto;

import com.cos.cercat.user.domain.User;

public record AlarmArg(
        User fromUser,
        Long postId
) {
    public static AlarmArg of(User fromUser, Long postId) {
        return new AlarmArg(
                fromUser,
                postId
        );
    }
}
