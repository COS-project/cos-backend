package com.cos.cercat.domain.post;

import lombok.Getter;

@Getter
public class PostStatus {
    private PostType postType;
    private int likeCount;
    private final int commentCount;

    public PostStatus(int commentCount, int likeCount, PostType postType) {
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.postType = postType;
    }

    public void likeCountUp() {
        likeCount++;
    }

    public void likeCountDown() {
        likeCount--;
    }
}
