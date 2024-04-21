package com.cos.cercat.apis.like.app.strategy;

import com.cos.cercat.apis.alarm.app.kafka.producer.AlarmProducer;
import com.cos.cercat.domain.AlarmType;
import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.dto.AlarmArg;
import com.cos.cercat.dto.AlarmEvent;
import com.cos.cercat.service.CommentLikeService;
import com.cos.cercat.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CommentLikeStrategy implements LikeStrategy<PostCommentEntity>{

    private final CommentLikeService commentLikeService;
    private final AlarmProducer alarmProducer;

    @Override
    public void flipLike(PostCommentEntity postCommentEntity, UserEntity userEntity) {
        CommentLikePK commentLikePK = CommentLikePK.of(userEntity.getId(), postCommentEntity.getId());

        if (commentLikeService.existsLike(commentLikePK)) {
            commentLikeService.deleteLike(postCommentEntity, commentLikePK);
            log.info("userEntity - {}, postCommentId - {} 댓글 좋아요 취소", userEntity.getEmail(), postCommentEntity.getId());
            return;
        }

        log.info("userEntity - {}, postCommentId - {} 댓글 좋아요 생성", userEntity.getEmail(), postCommentEntity.getId());
        commentLikeService.createLike(postCommentEntity, userEntity);
        alarmProducer.send(AlarmEvent.of(postCommentEntity.getUserEntity(), AlarmArg.of(userEntity, postCommentEntity.getPostEntity().getId()), AlarmType.NEW_LIKE_ON_COMMENT));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.COMMENT_LIKE_STRATEGY;
    }

    @Override
    public Class<PostCommentEntity> getGenericType() {
        return PostCommentEntity.class;
    }
}
