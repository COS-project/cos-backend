package com.cos.cercat.database.like.repository;

import com.cos.cercat.database.like.entity.CommentLikeEntity;
import com.cos.cercat.database.like.entity.PostLikeEntity;
import com.cos.cercat.database.post.entity.PostCommentEntity;
import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.database.post.repository.PostCommentJpaRepository;
import com.cos.cercat.database.post.repository.PostJpaRepository;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.database.user.repository.UserJpaRepository;
import com.cos.cercat.domain.like.Like;
import com.cos.cercat.domain.like.LikeRepository;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    private final PostLikeJpaRepository postLikeJpaRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final PostCommentJpaRepository commentJpaRepository;

    @Override
    public boolean isLiked(User user, Like like) {
        return switch (like.targetType()) {
            case POST -> postLikeJpaRepository.existsPostLikeByPostLikePK(user.getId(), like.targetId());
            case COMMENT -> commentLikeJpaRepository.existsCommentLikeByCommentLikePK(user.getId(), like.targetId());
        };
    }

    @Override
    @Transactional
    public void save(User user, Like like) {
        UserEntity userEntity = userJpaRepository.getReferenceById(user.getId());
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
    @Transactional
    public void remove(User user, Like like) {
        switch (like.targetType()) {
            case POST -> {
                postLikeJpaRepository.deleteByPostIdAndUserId(user.getId(), like.targetId());
            }
            case COMMENT -> {
                commentLikeJpaRepository.deleteByCommentIdAndUserId(user.getId(), like.targetId());
            }
        }
    }

}

