package com.cos.cercat.comment.app.usecase;

import com.cos.cercat.annotation.UseCase;
import com.cos.cercat.comment.service.PostCommentService;
import com.cos.cercat.user.service.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class PostCommentDeleteUseCase {

    private final PostCommentService postCommentService;
    private final UserService userService;

    /***
     * 댓글을 삭제합니다.
     *
     * @param commentId 댓글 ID
     * @param userId 유저 ID
     */
    @Transactional
    public void deletePostComment(Long commentId, Long userId) {
        User user = userService.getUser(userId);
        log.info("user - {}, commentId - {} 댓글 삭제", user.getEmail(), commentId);
        postCommentService.deletePostComment(commentId, user);
    }

}
