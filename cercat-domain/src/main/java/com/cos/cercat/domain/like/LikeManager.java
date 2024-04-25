package com.cos.cercat.domain.like;

import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeManager {

    private final LikeRepository likeRepository;
    private final PostReader postReader;
    private final PostUpdator postUpdator;

    public void like(TargetUser targetUser, Like like) {
        switch (like.targetType()) {
            case POST -> {
                Post post = postReader.readToLike(TargetPost.from(like.targetId()));
                post.like();
                postUpdator.update(post);
                likeRepository.save(targetUser, like);
            }
            case COMMENT -> {
                PostComment comment = postReader.readCommentToLike(TargetComment.from(like.targetId()));
                comment.like();
                postUpdator.updateComment(comment);
                likeRepository.save(targetUser, like);
            }
        }
    }

    public void unLike(TargetUser targetUser, Like like) {
        switch (like.targetType()) {
            case POST -> {
                Post post = postReader.readToLike(TargetPost.from(like.targetId()));
                post.unLike();
                postUpdator.update(post);
                likeRepository.remove(targetUser, like);
            }
            case COMMENT -> {
                PostComment comment = postReader.readCommentToLike(TargetComment.from(like.targetId()));
                comment.unLike();
                postUpdator.updateComment(comment);
                likeRepository.remove(targetUser, like);
            }
        }
    }

}
