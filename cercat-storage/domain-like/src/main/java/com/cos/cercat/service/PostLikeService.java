package com.cos.cercat.service;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.PostEntity;
import com.cos.cercat.repository.PostLikeJpaRepository;
import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.PostLikeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeJpaRepository postLikeJpaRepository;

    public boolean existsLike(PostLikePK postLikePK) {
        return postLikeJpaRepository.existsPostLikeByPostLikePK(postLikePK.getPostId(), postLikePK.getUserId());
    }

    public void createLike(PostEntity postEntity, UserEntity userEntity) {
        postLikeJpaRepository.save(PostLikeEntity.of(userEntity, postEntity));
        postEntity.increaseLikeCount();
    }

    public void deleteLike(PostEntity postEntity, PostLikePK postLikePK) {
        postLikeJpaRepository.deleteById(postLikePK);
        postEntity.decreaseLikeCount();
    }

}
