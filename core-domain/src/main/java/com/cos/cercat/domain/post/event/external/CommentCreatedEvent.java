package com.cos.cercat.domain.post.event.external;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.post.CommentContent;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.user.User;

public record CommentCreatedEvent(
        String eventId,
        User targetPostOwner,
        Long commentId,
        Long targetPostId,
        String targetPostTitle,
        CommentContent comment
) implements Event {

    public static Event from(Post post, PostComment postComment) {
        return new CommentCreatedEvent(
                Event.generateId(),
                post.getWriter(),
                postComment.getId(),
                post.getId(),
                post.getPostContent().title(),
                postComment.getContent()
        );
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
