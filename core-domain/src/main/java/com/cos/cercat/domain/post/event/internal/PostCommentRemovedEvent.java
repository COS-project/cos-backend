package com.cos.cercat.domain.post.event.internal;

import com.cos.cercat.domain.post.PostComment;

public record PostCommentRemovedEvent(
        Long postId,
        Long commentId
) {

    public static PostCommentRemovedEvent from(PostComment postComment) {
        return new PostCommentRemovedEvent(
                postComment.getPostId(),
                postComment.getId()
        );
    }
}
