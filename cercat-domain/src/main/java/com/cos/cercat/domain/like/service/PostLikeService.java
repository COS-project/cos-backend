package com.cos.cercat.domain.like.service;

import com.cos.cercat.domain.like.repository.PostLikeRepository;
import com.cos.cercat.domain.post.domain.Post;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.like.domain.PostLike;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public boolean existsLike(PostLikePK postLikePK) {
        return postLikeRepository.existsPostLikeByPostLikePK(postLikePK);
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
