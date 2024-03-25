package com.cos.cercat.like.service;

import com.cos.cercat.post.domain.Post;
import com.cos.cercat.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.like.domain.PostLike;
import com.cos.cercat.like.repository.PostLikeRepository;
import com.cos.cercat.user.domain.User;
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
