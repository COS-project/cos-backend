package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;
import lombok.Getter;

@Getter
public class BoardAlarm extends Alarm {
    private final User fromUser;
    private final Long postId;

    public BoardAlarm(User receiver, AlarmType alarmType, User fromUser, Long postId) {
        super(receiver, alarmType);
        this.fromUser = fromUser;
        this.postId = postId;
    }
}
