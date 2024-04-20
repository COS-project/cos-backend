package com.cos.cercat.service;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.PostEntity;
import com.cos.cercat.repository.PostLikeRepository;
import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.PostLike;
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

    public void createLike(PostEntity postEntity, UserEntity userEntity) {
        postLikeRepository.save(PostLike.of(userEntity, postEntity));
        postEntity.increaseLikeCount();
    }

    public void deleteLike(PostEntity postEntity, PostLikePK postLikePK) {
        postLikeRepository.deleteById(postLikePK);
        postEntity.decreaseLikeCount();
    }

}
