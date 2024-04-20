package com.cos.cercat.apis.like.app.strategy;

import com.cos.cercat.apis.alarm.app.kafka.producer.AlarmProducer;
import com.cos.cercat.domain.AlarmType;
import com.cos.cercat.domain.post.PostEntity;
import com.cos.cercat.dto.AlarmArg;
import com.cos.cercat.dto.AlarmEvent;
import com.cos.cercat.service.PostLikeService;
import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class PostLikeStrategy implements LikeStrategy<PostEntity> {

    private final PostLikeService postLikeService;
    private final AlarmProducer alarmProducer;

    @Override
    public void flipLike(PostEntity postEntity, UserEntity userEntity) {
        PostLikePK postLikePK = PostLikePK.of(userEntity.getId(), postEntity.getId());

        if (postLikeService.existsLike(postLikePK)) {
            postLikeService.deleteLike(postEntity, postLikePK);
            log.info("userEntity - {}, postId - {} 게시글 좋아요 취소", userEntity.getEmail(), postEntity.getId());
            return;
        }

        log.info("userEntity - {}, postId - {} 게시글 좋아요 생성", userEntity.getEmail(), postEntity.getId());
        postLikeService.createLike(postEntity, userEntity);
        alarmProducer.send(AlarmEvent.of(postEntity.getUserEntity(), AlarmArg.of(userEntity, postEntity.getId()), AlarmType.NEW_LIKE_ON_POST));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.POST_LIKE_STRATEGY;
    }

    @Override
    public Class<PostEntity> getGenericType() {
        return PostEntity.class;
    }
}
