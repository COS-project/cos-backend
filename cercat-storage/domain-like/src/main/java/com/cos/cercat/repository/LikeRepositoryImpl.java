package com.cos.cercat.repository;

import com.cos.cercat.domain.CommentLikeEntity;
import com.cos.cercat.domain.PostLikeEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.PostCommentEntity;
import com.cos.cercat.domain.like.Like;
import com.cos.cercat.domain.like.LikeRepository;
import com.cos.cercat.domain.PostEntity;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    //개념적으로 동일한 두 테이블에대한 의존
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final PostCommentJpaRepository commentJpaRepository;

    @Override
    public boolean isLiked(TargetUser targetUser, Like like) {
        return switch (like.targetType()) {
            case POST -> postLikeJpaRepository.existsPostLikeByPostLikePK(targetUser.userId(), like.targetId());
            case COMMENT ->
                    commentLikeJpaRepository.existsCommentLikeByCommentLikePK(targetUser.userId(), like.targetId());
        };
    }

    @Override
    public void save(TargetUser targetUser, Like like) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        switch (like.targetType()) {
            case POST -> {
                PostEntity postEntity = postJpaRepository.getReferenceById(like.targetId());
                postLikeJpaRepository.save(PostLikeEntity.of(userEntity, postEntity));
            }
            case COMMENT -> {
                PostCommentEntity commentEntity = commentJpaRepository.getReferenceById(like.targetId());
                commentLikeJpaRepository.save(CommentLikeEntity.of(userEntity, commentEntity));
            }
        }

    }

    @Override
    public void remove(TargetUser targetUser, Like like) {
        switch (like.targetType()) {
            case POST -> {
                postLikeJpaRepository.deleteByPostIdAndUserId(targetUser.userId(), like.targetId());
            }
            case COMMENT -> {
                commentLikeJpaRepository.deleteByCommentIdAndUserId(targetUser.userId(), like.targetId());
            }
        }
    }

}

