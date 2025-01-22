package com.cos.cercat.domain.post;

public record PostContent(
        String title,
        String content
) {

    public void update(PostContent newPostContent) {
    }
}
