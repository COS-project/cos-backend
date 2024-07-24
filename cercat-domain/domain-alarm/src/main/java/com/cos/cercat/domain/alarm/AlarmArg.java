package com.cos.cercat.domain.alarm;


import com.cos.cercat.domain.user.User;

public record AlarmArg(
        User fromUser,
        Long targetId
) {
    public static AlarmArg of(User fromUser, Long postId) {
        return new AlarmArg(
                fromUser,
                postId
        );
    }
}
