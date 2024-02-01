package com.cos.cercat.alarm.dto;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.user.domain.User;
import lombok.AllArgsConstructor;

public record AlarmArg(
        User fromUser,
        Long targetId
) {
    public static AlarmArg of(User fromUser, Long targetId) {
        return new AlarmArg(
                fromUser,
                targetId
        );
    }
}
