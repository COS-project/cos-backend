package com.cos.cercat.database.like.repository;

import com.cos.cercat.database.like.entity.LikeEntity;
import com.cos.cercat.domain.like.LikeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeJpaRepository extends JpaRepository<LikeEntity, Long>, LikeJpaCustomRepository {

    @Query("""
            select exists (select 1 from LikeEntity l
            where l.likeTargetType = :likeTargetType
            and l.userId = :userId
            and l.targetId = :targetId)
            """)
    boolean exists(Long userId, Long targetId, LikeTargetType likeTargetType);

    @Modifying
    @Query("""
            DELETE FROM LikeEntity l
            WHERE l.userId = :userId
            AND l.targetId = :targetId
            AND l.likeTargetType = :likeTargetType
            """)
    void delete(Long userId, Long targetId, LikeTargetType likeTargetType);
}
