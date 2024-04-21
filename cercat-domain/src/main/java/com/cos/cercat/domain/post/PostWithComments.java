package com.cos.cercat.domain.post;

import java.util.List;

public record PostWithComments(
        Post post,
        List<PostComment> postComments
) {
    public static PostWithComments of(Post post, List<PostComment> postComments) {
        return new PostWithComments(post, postComments);
    }
}
