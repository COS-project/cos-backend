package com.cos.cercat.domain.post;

public record CommentContent(
        Long parentId,
        String content
) {
    public boolean hasParent() {
        return parentId != null;
    }
}
