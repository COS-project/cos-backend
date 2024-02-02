package com.cos.cercat.comment.app;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.domain.PostComments;
import com.cos.cercat.comment.repository.PostCommentRepository;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<PostComment> getMyPostComments(User user, Pageable pageable) {
        return postCommentRepository.findPostCommentsByUser(user, pageable);
    }

    public void deletePostComment(Long commentId, User user) {

        PostComment postComment = getPostComment(commentId);

        if (postComment.isAuthorized(user)) {
            postCommentRepository.delete(postComment);
        }
    }

    public PostComment getPostCommentWithLock(Long commentId) {
        return postCommentRepository.findByIdWithPessimisticLock(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

}
