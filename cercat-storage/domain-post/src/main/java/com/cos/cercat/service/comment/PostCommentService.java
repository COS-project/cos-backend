package com.cos.cercat.service.comment;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.repository.comment.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    public PostComment getPostComment(Long commentId) {
        return postCommentRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public Slice<PostComment> getMyPostComments(UserEntity userEntity, Pageable pageable) {
        return postCommentRepository.findPostCommentsByUserEntity(userEntity, pageable);
    }

    public void deletePostComment(Long commentId, UserEntity userEntity) {
        PostComment postComment = getPostComment(commentId);

        if (postComment.isAuthorized(userEntity)) {
            postCommentRepository.delete(postComment);
            return;
        }
        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }

    public PostComment getPostCommentWithLock(Long commentId) {
        return postCommentRepository.findByIdWithPessimisticLock(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

}
