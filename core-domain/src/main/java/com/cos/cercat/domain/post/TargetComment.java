package com.cos.cercat.domain.post;

public record TargetComment(
        Long commentId
) {
    public static TargetComment from(Long commentId) {
        return new TargetComment(
                commentId
        );
    }
}
