package com.cos.cercat.domain.like;

import com.cos.cercat.domain.post.PostCommentReader;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostReader;
import com.cos.cercat.domain.post.CommentId;
import com.cos.cercat.domain.post.PostId;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeManager {

    private final LikeRepository likeRepository;
    private final PostReader postReader;
    private final PostCommentReader postCommentReader;
    private final LikeCounter likeCounter;
    private final ApplicationEventPublisher eventPublisher;

    public void like(User liker, LikeTarget likeTarget) {
        switch (likeTarget.targetType()) {
            case POST -> {
                Post post = postReader.read(PostId.from(likeTarget.targetId()));
                likeRepository.save(Like.from(liker, likeTarget));
                eventPublisher.publishEvent(LikeCreatedEvent.postLike(post, liker));
            }
            case COMMENT -> {
                PostComment comment = postCommentReader.read(CommentId.from(likeTarget.targetId()));
                likeRepository.save(Like.from(liker, likeTarget));
                eventPublisher.publishEvent(LikeCreatedEvent.commentLike(comment, liker));
            }
        }
        likeCounter.countUp(likeTarget);
    }

    public void unLike(User liker, LikeTarget likeTarget) {
        likeRepository.remove(Like.from(liker, likeTarget));
        likeCounter.countDown(likeTarget);
    }

}
