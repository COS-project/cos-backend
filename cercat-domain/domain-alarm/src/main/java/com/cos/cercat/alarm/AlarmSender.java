package com.cos.cercat.alarm;

import com.cos.cercat.post.Post;
import com.cos.cercat.user.User;

public interface AlarmSender {
    void send(User user, Post post, AlarmType alarmType);
}
