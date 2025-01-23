package com.cos.cercat.domain.like;

import com.cos.cercat.domain.common.EventPublisher;
import com.cos.cercat.domain.post.CommentReader;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostReader;
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
    private final CommentReader commentReader;
    private final LikeCounter likeCounter;
    private final EventPublisher eventPublisher;

    public void like(User liker, LikeTarget likeTarget) {
        switch (likeTarget.targetType()) {
            case POST -> {
                Post post = postReader.read(TargetPost.from(likeTarget.targetId()));
                likeRepository.save(Like.from(liker, likeTarget));
                eventPublisher.publish(LikeCreatedEvent.postLike(post, liker));
            }
            case COMMENT -> {
                PostComment comment = commentReader.read(TargetComment.from(likeTarget.targetId()));
                likeRepository.save(Like.from(liker, likeTarget));
                eventPublisher.publish(LikeCreatedEvent.commentLike(comment, liker));
            }
        }
        likeCounter.countUp(likeTarget);
    }

    public void unLike(User liker, LikeTarget likeTarget) {
        likeRepository.remove(Like.from(liker, likeTarget));
        likeCounter.countDown(likeTarget);
    }

}
