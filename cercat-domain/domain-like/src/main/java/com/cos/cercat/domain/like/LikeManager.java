package com.cos.cercat.domain.like;

import com.cos.cercat.domain.alarm.AlarmManager;
import com.cos.cercat.domain.alarm.AlarmPublisher;
import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeManager {

    private final LikeRepository likeRepository;
    private final PostReader postReader;
    private final PostUpdator postUpdator;
    private final CommentUpdator commentUpdator;
    private final AlarmManager alarmManager;
    private final CommentReader commentReader;

    public void like(User user, Like like) {
        switch (like.targetType()) {
            case POST -> {
                Post post = postReader.readToLike(TargetPost.from(like.targetId()));
                post.like();
                postUpdator.update(post);
                likeRepository.save(user, like);
                alarmManager.publish(post.getUser(), user, post.getId(),
                        AlarmType.NEW_LIKE_ON_POST);
            }
            case COMMENT -> {
                PostComment comment = commentReader.readToLike(TargetComment.from(like.targetId()));
                comment.like();
                commentUpdator.update(comment);
                likeRepository.save(user, like);
                alarmManager.publish(comment.getUser(), user, comment.getId(),
                        AlarmType.NEW_LIKE_ON_POST);
            }
        }
    }

    public void unLike(User user, Like like) {
        switch (like.targetType()) {
            case POST -> {
                Post post = postReader.readToLike(TargetPost.from(like.targetId()));
                post.unLike();
                postUpdator.update(post);
                likeRepository.remove(user, like);
            }
            case COMMENT -> {
                PostComment comment = commentReader.readToLike(TargetComment.from(like.targetId()));
                comment.unLike();
                commentUpdator.update(comment);
                likeRepository.remove(user, like);
            }
        }
    }

}
