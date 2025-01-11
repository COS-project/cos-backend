package com.cos.cercat.domain.post;

import com.cos.cercat.domain.alarm.AlarmManager;
import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.post.exception.InvalidCommentException;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAppender {

    private final CreatePostRepository createPostRepository;
    private final AlarmManager alarmManager;
    private final CommentReader commentReader;

    public void append(User user, Post post, CommentContent comment) {
        if (comment.hasParent()) {
            appendChild(user, post, comment);
            return;
        }
        createPostRepository.saveComment(user, post, comment);
        alarmManager.publish(post.getUser(), user, post.getId(), AlarmType.NEW_COMMENT_ON_POST);
    }

    private void appendChild(User user, Post post, CommentContent content) {
        PostComment parentComment = commentReader.read(TargetComment.from(content.parentId()));

        if (!parentComment.isCommentIn(post)) {
            throw InvalidCommentException.EXCEPTION;
        }

        createPostRepository.saveComment(user, post, content);
        alarmManager.publish(post.getUser(), user, post.getId(), AlarmType.NEW_COMMENT_ON_POST);
        alarmManager.publish(parentComment.getUser(), user, post.getId(),
                AlarmType.REPLY_ON_COMMENT);
    }
}
