package com.cos.cercat.alarm;

import com.cos.cercat.user.User;
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
