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
    private final EventPublisher eventPublisher;

    public void like(User user, LikeTarget likeTarget) {
        switch (likeTarget.targetType()) {
            case POST -> {
                Post post = postReader.read(TargetPost.from(likeTarget.targetId()));
                likeRepository.save(user, likeTarget);
                eventPublisher.publish(LikeCreatedEvent.postLike(post, user));
            }
            case COMMENT -> {
                PostComment comment = commentReader.read(TargetComment.from(likeTarget.targetId()));
                likeRepository.save(user, likeTarget);
                eventPublisher.publish(LikeCreatedEvent.commentLike(comment, user));
            }
        }
    }

    public void unLike(User user, LikeTarget likeTarget) {
        // likeCounter.decrease(likeTarget.targetId());
        likeRepository.remove(user, likeTarget);
    }

    public void initCount(LikeTarget likeTarget) {
        likeRepository.initLikeCount(likeTarget);

    }


}
