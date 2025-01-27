package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.Event;
import com.cos.cercat.domain.user.User;

public record CommentCreatedEvent(
        User targetPostOwner,
        Long targetPostId,
        String targetPostTitle,
        CommentContent comment
) implements Event {

    public static Event from(Post post, CommentContent commentContent) {
        return new CommentCreatedEvent(post.getWriter(), post.getId(), post.getPostContent().title(), commentContent);
    }

    @Override
    public String getKey() {
        return targetPostId.toString();
    }

    @Override
    public String getType() {
        return "comment-created";
    }
}
