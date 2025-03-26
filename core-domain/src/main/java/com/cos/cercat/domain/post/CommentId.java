package com.cos.cercat.domain.post;

public record CommentId(
        Long commentId
) {
    public static CommentId from(Long commentId) {
        return new CommentId(
                commentId
        );
    }
}
