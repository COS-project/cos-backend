package com.cos.cercat.domain.like;

import com.cos.cercat.domain.common.EventPublisher;
import com.cos.cercat.domain.post.CommentReader;
import com.cos.cercat.domain.post.CommentUpdator;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostReader;
import com.cos.cercat.domain.post.PostUpdator;
import com.cos.cercat.domain.post.TargetComment;
import com.cos.cercat.domain.post.TargetPost;
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
    private final CommentReader commentReader;
    private final EventPublisher eventPublisher;

    public void like(User user, Like like) {
        switch (like.targetType()) {
            case POST -> {
                Post post = postReader.readToLike(TargetPost.from(like.targetId()));
                post.like();
                postUpdator.update(post);
                likeRepository.save(user, like);
                eventPublisher.publish(LikeCreatedEvent.postLike(post, user));
            }
            case COMMENT -> {
                PostComment comment = commentReader.readToLike(TargetComment.from(like.targetId()));
                comment.like();
                commentUpdator.update(comment);
                likeRepository.save(user, like);
                eventPublisher.publish(LikeCreatedEvent.commentLike(comment, user));
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
