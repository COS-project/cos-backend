package com.cos.cercat.post;

import com.cos.cercat.alarm.AlarmPublisher;
import com.cos.cercat.alarm.AlarmType;
import com.cos.cercat.exception.InvalidCommentException;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAppender {

    private final CreatePostRepository createPostRepository;
    private final AlarmPublisher alarmPublisher;
    private final CommentReader commentReader;

    public void append(User user, Post post, CommentContent comment) {
        if (comment.hasParent()) {
            appendChild(user, post, comment);
            return;
        }
        createPostRepository.saveComment(user, post, comment);
        alarmPublisher.publish(post.getUser(), user, post.getId(), AlarmType.NEW_COMMENT_ON_POST);
    }

    private void appendChild(User user, Post post, CommentContent content) {
        PostComment parentComment = commentReader.read(TargetComment.from(content.parentId()));

        if (!parentComment.isCommentIn(post)) {
            throw InvalidCommentException.EXCEPTION;
        }

        createPostRepository.saveComment(user, post, content);
        alarmPublisher.publish(post.getUser(), user, post.getId(), AlarmType.NEW_COMMENT_ON_POST);
        alarmPublisher.publish(parentComment.getUser(), user, post.getId(), AlarmType.REPLY_ON_COMMENT);
    }
}
