package com.cos.cercat.repository;

import com.cos.cercat.domain.like.CommentLikeRepository;
import com.cos.cercat.domain.like.TargetComment;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeCoreRepository implements CommentLikeRepository {

    private final CommentLikeJpaRepository commentLikeJpaRepository;

    @Override
    public boolean isLiked(TargetUser targetUser, TargetComment targetComment) {
        return commentLikeJpaRepository.existsCommentLikeByCommentLikePK(targetComment.commentId(), targetUser.userId());
    }
}
