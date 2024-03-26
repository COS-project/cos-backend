package com.cos.cercat.apis.like.app.strategy;

import com.cos.cercat.apis.alarm.app.kafka.producer.AlarmProducer;
import com.cos.cercat.domain.alarm.domain.AlarmType;
import com.cos.cercat.domain.alarm.dto.AlarmArg;
import com.cos.cercat.domain.alarm.dto.AlarmEvent;
import com.cos.cercat.domain.like.service.PostLikeService;
import com.cos.cercat.domain.post.domain.Post;
import com.cos.cercat.domain.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.user.domain.User;
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
    public void flipLike(Post post, User user) {
        PostLikePK postLikePK = PostLikePK.of(user.getId(), post.getId());

        if (postLikeService.existsLike(postLikePK)) {
            postLikeService.deleteLike(post, postLikePK);
            log.info("user - {}, postId - {} 게시글 좋아요 취소", user.getEmail(), post.getId());
            return;
        }

        log.info("user - {}, postId - {} 게시글 좋아요 생성", user.getEmail(), post.getId());
        postLikeService.createLike(post, user);
        alarmProducer.send(AlarmEvent.of(post.getUser(), AlarmArg.of(user, post.getId()), AlarmType.NEW_LIKE_ON_POST));
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
