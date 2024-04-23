package com.cos.cercat.apis.post.dto.request;


import com.cos.cercat.domain.post.CommentContent;

public record PostCommentCreateRequest(
        Long parentCommentId,
        String content
) {
    public CommentContent toCommentContent() {
        return new CommentContent(parentCommentId, content);
    }
}
