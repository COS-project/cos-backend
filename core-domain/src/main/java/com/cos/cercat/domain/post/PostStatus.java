package com.cos.cercat.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class PostStatus {
    private PostType postType;
    private final int commentCount;

    public PostStatus(int commentCount, PostType postType) {
        this.commentCount = commentCount;
        this.postType = postType;
    }
}
