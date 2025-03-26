package com.cos.cercat.domain.post;

public record PostId(
        long postId
) {
    public static PostId from(long postId) {
        return new PostId(
                postId
        );
    }
}
