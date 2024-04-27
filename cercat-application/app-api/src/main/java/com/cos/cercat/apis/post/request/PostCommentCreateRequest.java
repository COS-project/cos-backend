package com.cos.cercat.apis.post.request;


import com.cos.cercat.post.CommentContent;

public record PostCommentCreateRequest(
        Long parentCommentId,
        String content
) {
    public CommentContent toCommentContent() {
        return new CommentContent(parentCommentId, content);
    }
}
