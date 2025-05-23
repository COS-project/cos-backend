package com.cos.cercat.domain.like;

import com.cos.cercat.domain.like.event.external.LikeCreatedEvent;
import com.cos.cercat.domain.like.exception.LikeException;
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
        if (likeRepository.exists(Like.from(liker, likeTarget))) {
            throw LikeException.alreadyLiked();
        }

        switch (likeTarget.targetType()) {
            case POST -> likePost(liker, likeTarget);
            case COMMENT -> likePostComment(liker, likeTarget);
        }

        likeCounter.countUp(likeTarget);
    }

    public void unLike(User liker, LikeTarget likeTarget) {
        if (!likeRepository.exists(Like.from(liker, likeTarget))) {
            throw LikeException.alreadyNotLiked();
        }

        likeRepository.remove(Like.from(liker, likeTarget));
        likeCounter.countDown(likeTarget);
    }

    private void likePost(User liker, LikeTarget likeTarget) {
        Post post = postReader.read(PostId.from(likeTarget.targetId()));
        likeRepository.save(Like.from(liker, likeTarget));

        if (!post.isOwner(liker)) {
            eventPublisher.publishEvent(LikeCreatedEvent.postLike(post, liker));
        }
    }

    private void likePostComment(User liker, LikeTarget likeTarget) {
        PostComment comment = postCommentReader.read(CommentId.from(likeTarget.targetId()));
        likeRepository.save(Like.from(liker, likeTarget));
        if (!comment.isOwner(liker)) {
            eventPublisher.publishEvent(LikeCreatedEvent.commentLike(comment, liker));
        }
    }

}
