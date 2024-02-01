package com.cos.cercat.like.app;

import com.cos.cercat.alarm.app.producer.AlarmProducer;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.alarm.dto.AlarmArg;
import com.cos.cercat.alarm.dto.AlarmEvent;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.like.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentLikeStrategy implements LikeStrategy<PostComment>{

    private final CommentLikeService commentLikeService;
    private final AlarmProducer alarmProducer;

    @Override
    public void flipLike(PostComment postComment, User user) {
        CommentLikePK commentLikePK = CommentLikePK.of(user.getId(), postComment.getId());

        if (commentLikeService.existsLike(commentLikePK)) {
            commentLikeService.deleteLike(postComment, commentLikePK);
            return;
        }

        commentLikeService.createLike(postComment, user);
        alarmProducer.send(AlarmEvent.of(postComment.getUser(), AlarmArg.of(user, postComment.getId()), AlarmType.NEW_LIKE_ON_COMMENT));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.COMMENT_LIKE_STRATEGY;
    }

    @Override
    public Class<PostComment> getGenericType() {
        return PostComment.class;
    }
}
