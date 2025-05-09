package com.cos.cercat.database.like.repository;

import com.cos.cercat.database.like.entity.LikeCountEntity;
import com.cos.cercat.database.like.entity.LikeCountId;
import com.cos.cercat.database.like.entity.LikeEntity;
import com.cos.cercat.domain.like.Like;
import com.cos.cercat.domain.like.LikeCount;
import com.cos.cercat.domain.like.LikeCountRepository;
import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeRepository;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class LikeRepositoryAdapter implements LikeRepository, LikeCountRepository {

    private final LikeJpaRepository likeJpaRepository;
    private final LikeCountJpaRepository likeCountJpaRepository;

    @Override
    public void save(Like like) {
        likeJpaRepository.save(LikeEntity.from(like));
    }

    @Override
    public boolean exists(Like like) {
        return likeJpaRepository.exists(like.likerId(), like.likeTarget().targetId(), like.likeTarget().targetType());
    }

    @Override
    public Map<Long, Boolean> existsMap(
            User liker,
            List<LikeTarget> likeTargets
    ) {
        return likeJpaRepository.existsMap(
                liker,
                likeTargets
        );
    }

    @Override
    public void remove(Like like) {
        likeJpaRepository.delete(like.likerId(), like.likeTarget().targetId(), like.likeTarget().targetType());
    }

    @Override
    public void save(LikeCount likeCount) {
        likeCountJpaRepository.save(LikeCountEntity.from(likeCount));
    }

    @Override
    public Optional<LikeCount> findByTarget(LikeTarget likeTarget) {
        return likeCountJpaRepository.findById(LikeCountId.from(likeTarget)).map(LikeCountEntity::toDomain);
    }

    @Override
    public Map<Long, LikeCount> findByTargets(List<LikeTarget> likeTargets) {
        List<LikeCountEntity> likeCounts = likeCountJpaRepository.findByIds(
                likeTargets.stream().map(LikeCountId::from).toList());
        return likeCounts.stream()
                .collect(Collectors.toMap(
                        likeCountEntity -> likeCountEntity.getId().getTargetId(),
                        LikeCountEntity::toDomain
                ));
    }

    @Override
    public void update(List<LikeCount> bufferedCounts) {
        likeCountJpaRepository.batchUpdate(bufferedCounts);
    }

    @Override
    public void delete(LikeTarget target) {
        likeCountJpaRepository.deleteById(LikeCountId.from(target));
    }
}

