package com.cos.cercat.post;

public record TargetPost(
        long postId
) {
    public static TargetPost from(long postId) {
        return new TargetPost(
                postId
        );
    }
}
