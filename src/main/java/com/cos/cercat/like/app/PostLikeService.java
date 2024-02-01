package com.cos.cercat.like.app;

import com.cos.cercat.alarm.app.producer.AlarmProducer;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.alarm.dto.AlarmArg;
import com.cos.cercat.alarm.dto.AlarmEvent;
import com.cos.cercat.board.domain.Post;
import com.cos.cercat.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.like.domain.PostLike;
import com.cos.cercat.like.repository.PostLikeRepository;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public boolean existsLike(PostLikePK postLikePK) {
        return postLikeRepository.existsById(postLikePK);
    }

    public void createLike(Post post, User user) {
        postLikeRepository.save(PostLike.of(user, post));
        post.increaseLikeCount();
    }

    public void deleteLike(Post post, PostLikePK postLikePK) {
        postLikeRepository.deleteById(postLikePK);
        post.decreaseLikeCount();
    }

}
