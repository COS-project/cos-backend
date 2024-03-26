package com.cos.cercat.apis.like.app.strategy;

import com.cos.cercat.apis.alarm.app.kafka.producer.AlarmProducer;
import com.cos.cercat.domain.AlarmType;
import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.dto.AlarmArg;
import com.cos.cercat.dto.AlarmEvent;
import com.cos.cercat.service.CommentLikeService;
import com.cos.cercat.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CommentLikeStrategy implements LikeStrategy<PostComment>{

    private final CommentLikeService commentLikeService;
    private final AlarmProducer alarmProducer;

    @Override
    public void flipLike(PostComment postComment, User user) {
        CommentLikePK commentLikePK = CommentLikePK.of(user.getId(), postComment.getId());

        if (commentLikeService.existsLike(commentLikePK)) {
            commentLikeService.deleteLike(postComment, commentLikePK);
            log.info("user - {}, postCommentId - {} 댓글 좋아요 취소", user.getEmail(), postComment.getId());
            return;
        }

        log.info("user - {}, postCommentId - {} 댓글 좋아요 생성", user.getEmail(), postComment.getId());
        commentLikeService.createLike(postComment, user);
        alarmProducer.send(AlarmEvent.of(postComment.getUser(), AlarmArg.of(user, postComment.getPost().getId()), AlarmType.NEW_LIKE_ON_COMMENT));
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
