package com.cos.cercat.domain.like;

import com.cos.cercat.domain.common.Event;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.user.User;

public record LikeCreatedEvent(
        Long targetId,
        User targetOwner,
        Long likerId,
        String likerNickname,
        LikeTargetType targetType
) implements Event {

    public static Event postLike(Post post, User liker) {
        return new LikeCreatedEvent(post.getId(), post.getUser(), liker.getId(), liker.getNickname(), LikeTargetType.POST);
    }

    public static Event commentLike(PostComment comment, User liker) {
        return new LikeCreatedEvent(comment.getId(), comment.getUser(), liker.getId(), liker.getNickname(), LikeTargetType.COMMENT);
    }

    @Override
    public String getKey() {
        return targetId.toString();
    }

    @Override
    public String getType() {
        return "like-created";
    }
}
