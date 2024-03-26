package com.cos.cercat.apis.comment.dto.request;


import com.cos.cercat.domain.User;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.domain.post.Post;

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
