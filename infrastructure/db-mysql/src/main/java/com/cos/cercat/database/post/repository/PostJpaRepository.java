package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.post.entity.PostEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.userEntity.id = :userId
            """)
    Slice<PostEntity> findByUserId(Long userId, Pageable pageable);

    @Query("""
            SELECT p
            FROM PostEntity p
            LEFT JOIN LikeCountEntity lc ON p.id = lc.id.targetId AND lc.id.targetType = 'POST'
            WHERE p.postType = 'TIP'
            AND p.certificateEntity.id = :certificateId
            ORDER BY lc.count DESC
            LIMIT 3
            """)
    List<PostEntity> findTop3Tips(Long certificateId);
}
