package com.cos.cercat.service;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.domain.CommentLikeEntity;
import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.repository.CommentLikeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeJpaRepository commentLikeJpaRepository;

    public boolean existsLike(CommentLikePK commentLikePK) {
        return commentLikeJpaRepository.existsCommentLikeByCommentLikePK(commentLikePK.getCommentId(), commentLikePK.getUserId());
    }

    public void createLike(PostCommentEntity postCommentEntity, UserEntity userEntity) {
        commentLikeJpaRepository.save(CommentLikeEntity.of(userEntity, postCommentEntity));
        postCommentEntity.increaseLikeCount();
    }

    public void deleteLike(PostCommentEntity postCommentEntity, CommentLikePK commentLikePK) {
        commentLikeJpaRepository.deleteById(commentLikePK);
        postCommentEntity.decreaseLikeCount();
    }
}
