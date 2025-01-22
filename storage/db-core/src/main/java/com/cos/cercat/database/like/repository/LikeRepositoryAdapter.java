package com.cos.cercat.database.like.repository;

import com.cos.cercat.database.like.entity.LikeCountEntity;
import com.cos.cercat.database.like.entity.LikeEntity;
import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeRepository;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryAdapter implements LikeRepository {

    private final LikeJpaRepository likeJpaRepository;
    private final LikeCountJpaRepository likeCountJpaRepository;

    @Override
    public boolean isLiked(User user, LikeTarget likeTarget) {
        return likeJpaRepository.exists(user.getId(), likeTarget.targetId(), likeTarget.targetType());
    }

    @Override
    @Transactional
    public void save(User user, LikeTarget likeTarget) {
        likeJpaRepository.save(LikeEntity.of(user, likeTarget));
    }

    @Override
    @Transactional
    public void remove(User user, LikeTarget likeTarget) {
        likeJpaRepository.delete(user.getId(), likeTarget.targetId(), likeTarget.targetType());
    }

    @Override
    public void initLikeCount(LikeTarget likeTarget) {
        likeCountJpaRepository.save(LikeCountEntity.from(likeTarget));
    }

    @Override
    public Long getCount(LikeTarget likeTarget) {
        return 0L;
    }
}

