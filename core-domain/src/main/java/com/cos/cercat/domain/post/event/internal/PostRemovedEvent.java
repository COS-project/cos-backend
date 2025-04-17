package com.cos.cercat.domain.post.event.internal;

import com.cos.cercat.domain.post.Post;

public record PostRemovedEvent(
        Long postId
) {
    public static PostRemovedEvent from(Post post) {
        return new PostRemovedEvent(
                post.getId()
        );
    }
}
