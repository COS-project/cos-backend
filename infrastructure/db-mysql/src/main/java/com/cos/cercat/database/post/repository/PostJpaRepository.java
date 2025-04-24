package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.PostType;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long>, PostRepositoryCustom {

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.userEntity.id = :userId
            AND p.postType = :postType
            """)
    Slice<PostEntity> findByUserIdAndPostType(Long userId, PostType postType, Pageable pageable);

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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PostEntity p WHERE p.id = :postId")
    Optional<PostEntity> findForUpdate(Long postId);
}
