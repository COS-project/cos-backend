package com.cos.cercat.service.comment;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.repository.comment.PostCommentJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentJpaRepository postCommentJpaRepository;

    public PostCommentEntity getPostComment(Long commentId) {
        return postCommentJpaRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public Slice<PostCommentEntity> getMyPostComments(UserEntity userEntity, Pageable pageable) {
        return postCommentJpaRepository.findPostCommentsByUserEntity(userEntity, pageable);
    }

    public void deletePostComment(Long commentId, UserEntity userEntity) {
        PostCommentEntity postCommentEntity = getPostComment(commentId);

        if (postCommentEntity.isAuthorized(userEntity)) {
            postCommentJpaRepository.delete(postCommentEntity);
            return;
        }
        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }

    public PostCommentEntity getPostCommentWithLock(Long commentId) {
        return postCommentJpaRepository.findByIdWithPessimisticLock(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

}
