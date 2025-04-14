package com.cos.cercat.domain.like;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.user.User;
import java.util.UUID;

public record LikeCreatedEvent(
        String eventId,
        Long targetId,
        User targetOwner,
        Long likerId,
        String likerNickname,
        LikeTargetType targetType
) implements Event {

    public static Event postLike(Post post, User liker) {
        return new LikeCreatedEvent(
                UUID.randomUUID().toString(),
                post.getId(),
                post.getWriter(),
                liker.getId(),
                liker.getNickname(),
                LikeTargetType.POST
        );
    }

    public static Event commentLike(PostComment comment, User liker) {
        return new LikeCreatedEvent(
                UUID.randomUUID().toString(),
                comment.getId(),
                comment.getOwner(),
                liker.getId(),
                liker.getNickname(),
                LikeTargetType.COMMENT
        );
    }

    @Override
    public String resolveId() {
        return eventId;
    }

    @Override
    public String resolveKey() {
        return targetId.toString();
    }

    @Override
    public String resolveType() {
        return "like-created";
    }
}
