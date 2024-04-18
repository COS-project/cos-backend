package com.cos.cercat.apis.like.app.strategy;

import com.cos.cercat.apis.alarm.app.kafka.producer.AlarmProducer;
import com.cos.cercat.domain.AlarmType;
import com.cos.cercat.domain.post.Post;
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
public class PostLikeStrategy implements LikeStrategy<Post> {

    private final PostLikeService postLikeService;
    private final AlarmProducer alarmProducer;

    @Override
    public void flipLike(Post post, UserEntity userEntity) {
        PostLikePK postLikePK = PostLikePK.of(userEntity.getId(), post.getId());

        if (postLikeService.existsLike(postLikePK)) {
            postLikeService.deleteLike(post, postLikePK);
            log.info("userEntity - {}, postId - {} 게시글 좋아요 취소", userEntity.getEmail(), post.getId());
            return;
        }

        log.info("userEntity - {}, postId - {} 게시글 좋아요 생성", userEntity.getEmail(), post.getId());
        postLikeService.createLike(post, userEntity);
        alarmProducer.send(AlarmEvent.of(post.getUserEntity(), AlarmArg.of(userEntity, post.getId()), AlarmType.NEW_LIKE_ON_POST));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.POST_LIKE_STRATEGY;
    }

    @Override
    public Class<Post> getGenericType() {
        return Post.class;
    }
}
