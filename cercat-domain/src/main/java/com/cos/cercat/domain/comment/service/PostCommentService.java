package com.cos.cercat.domain.comment.service;

import com.cos.cercat.domain.comment.domain.PostComment;
import com.cos.cercat.domain.comment.repository.PostCommentRepository;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
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

    public Slice<PostComment> getMyPostComments(User user, Pageable pageable) {
        return postCommentRepository.findPostCommentsByUser(user, pageable);
    }

    public void deletePostComment(Long commentId, User user) {
        PostComment postComment = getPostComment(commentId);

        if (postComment.isAuthorized(user)) {
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
