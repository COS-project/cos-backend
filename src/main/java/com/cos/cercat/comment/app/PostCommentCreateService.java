package com.cos.cercat.comment.app;

import com.cos.cercat.board.app.PostService;
import com.cos.cercat.board.domain.Post;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.dto.request.PostCommentCreateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCommentCreateService {

    private final PostCommentService postCommentService;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    public void createPostComment(Long postId, PostCommentCreateRequest request, Long userId) {
        Post post = postService.getPost(postId);
        User user = userService.getUser(userId);

        PostComment postComment = request.toEntity(post, user);

        if (request.parentCommentId() != null) {
            PostComment parentComment = postCommentService.getPostComment(request.parentCommentId());
            parentComment.addChildComment(postComment);
            return;
        }

        post.addComment(postComment);
    }


}
