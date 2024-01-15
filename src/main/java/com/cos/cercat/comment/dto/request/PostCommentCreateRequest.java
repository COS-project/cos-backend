package com.cos.cercat.comment.dto.request;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.user.domain.User;

public record PostCommentCreateRequest(
        Long postId,
        Long parentCommentId,
        String content
) {
    public PostComment toEntity(Post post, User user) {
        return PostComment.of(
                user,
                post,
                content
        );
    }
}
