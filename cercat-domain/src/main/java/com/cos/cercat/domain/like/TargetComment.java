package com.cos.cercat.domain.like;

public record TargetComment(
        Long commentId
) {
    public static TargetComment from(Long commentId) {
        return new TargetComment(
                commentId
        );
    }
}
