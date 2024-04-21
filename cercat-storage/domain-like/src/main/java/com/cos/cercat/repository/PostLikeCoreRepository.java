package com.cos.cercat.repository;

import com.cos.cercat.domain.like.PostLikeRepository;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeCoreRepository implements PostLikeRepository {

    private final PostLikeJpaRepository postLikeJpaRepository;

    @Override
    public boolean isLiked(TargetUser targetUser, TargetPost targetPost) {
        return postLikeJpaRepository.existsPostLikeByPostLikePK(targetUser.userId(), targetPost.postId());
    }
}
