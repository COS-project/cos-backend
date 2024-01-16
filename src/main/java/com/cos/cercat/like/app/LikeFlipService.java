package com.cos.cercat.like.app;

import com.cos.cercat.board.app.PostService;
import com.cos.cercat.board.domain.Post;
import com.cos.cercat.comment.app.PostCommentService;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.like.dto.request.LikeType;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeFlipService {

    private final PostService postService;
    private final PostCommentService postCommentService;
    private final UserService userService;
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;


    @Transactional
    public void flipLike(LikeType likeType, Long id, Long userId) {

        User user = userService.getUser(userId);

        switch (likeType) {
            case POST -> {
                Post post = postService.getPostWithLock(id);
                postLikeService.flipPostLike(post, user);
            }
            case COMMENT -> {
                PostComment postComment = postCommentService.getPostCommentWithLock(id);
                commentLikeService.flipPostLike(postComment, user);
            }
        }
    }
}
