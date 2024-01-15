package com.cos.cercat.comment.app;

import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCommentDeleteService {

    private final PostCommentService postCommentService;
    private final UserService userService;

    public void deletePostComment(Long commentId, Long userId) {
        User user = userService.getUser(userId);

        postCommentService.deletePostComment(commentId, user);

    }

}
