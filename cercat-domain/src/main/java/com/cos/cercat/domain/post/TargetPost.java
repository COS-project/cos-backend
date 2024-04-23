package com.cos.cercat.domain.post;

public record TargetPost(
        long postId
) {
    public static TargetPost from(long postId) {
        return new TargetPost(
                postId
        );
    }
}
