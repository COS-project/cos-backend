package com.cos.cercat.apis.comment.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.service.UserService;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.service.comment.PostCommentService;
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
        UserEntity userEntity = userService.getUser(userId);
        log.info("userEntity - {}, commentId - {} 댓글 삭제", userEntity.getEmail(), commentId);
        postCommentService.deletePostComment(commentId, userEntity);
    }

}
