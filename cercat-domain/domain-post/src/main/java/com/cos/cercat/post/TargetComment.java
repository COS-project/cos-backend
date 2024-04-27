package com.cos.cercat.post;

public record TargetComment(
        Long commentId
) {
    public static TargetComment from(Long commentId) {
        return new TargetComment(
                commentId
        );
    }
}
