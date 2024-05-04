package com.cos.cercat.post;

import com.cos.cercat.alarm.AlarmSender;
import com.cos.cercat.alarm.AlarmType;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAppender {

    private final CreatePostRepository createPostRepository;
    private final AlarmSender alarmSender;

    public void append(User user, Post post, CommentContent content) {
        createPostRepository.saveComment(user, post, content);
        alarmSender.send(user, post, AlarmType.NEW_COMMENT_ON_POST);
    }

    public void appendChild(User user, Post post, CommentContent commentContent) {
        append(user, post, commentContent);
        alarmSender.send(user, post, AlarmType.NEW_COMMENT_ON_POST);
        alarmSender.send(user, post, AlarmType.REPLY_ON_COMMENT);
    }



}
