package com.cos.cercat.database.like.repository;

import static com.cos.cercat.database.like.entity.QLikeEntity.likeEntity;

import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeJpaCustomRepositoryImpl implements LikeJpaCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, Boolean> existsMap(
            User liker,
            List<LikeTarget> likeTargets
    ) {
        List<Long> targetIds = likeTargets.stream().map(LikeTarget::targetId).toList();
        LikeTargetType likeTargetType = likeTargets.get(0).targetType();

        List<Long> likedIds = queryFactory.select(likeEntity.targetId)
                .from(likeEntity)
                .where(
                        likeEntity.userId.eq(liker.getId()),
                        likeEntity.likeTargetType.eq(likeTargetType),
                        likeEntity.targetId.in(targetIds)
                )
                .fetch();

        return targetIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        likedIds::contains
                ));
    }
}
