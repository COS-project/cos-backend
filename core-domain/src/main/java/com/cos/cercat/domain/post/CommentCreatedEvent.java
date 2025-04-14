package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.user.User;
import java.util.UUID;

public record CommentCreatedEvent(
        String eventId,
        User targetPostOwner,
        Long targetPostId,
        String targetPostTitle,
        CommentContent comment
) implements Event {

    public static Event from(Post post, CommentContent commentContent) {
        return new CommentCreatedEvent(UUID.randomUUID().toString(), post.getWriter(), post.getId(), post.getPostContent().title(), commentContent);
    }

    @Override
    public String resolveId() {
        return eventId;
    }

    @Override
    public String resolveKey() {
        return targetPostId.toString();
    }

    @Override
    public String resolveType() {
        return "comment-created";
    }
}
