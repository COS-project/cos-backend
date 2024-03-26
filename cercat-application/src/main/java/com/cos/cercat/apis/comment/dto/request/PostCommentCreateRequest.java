package com.cos.cercat.apis.comment.dto.request;

import com.cos.cercat.domain.post.domain.Post;
import com.cos.cercat.domain.comment.domain.PostComment;
import com.cos.cercat.domain.user.domain.User;

public record PostCommentCreateRequest(
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
