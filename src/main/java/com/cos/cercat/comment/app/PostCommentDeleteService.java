package com.cos.cercat.comment.app;

import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCommentDeleteService {

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
