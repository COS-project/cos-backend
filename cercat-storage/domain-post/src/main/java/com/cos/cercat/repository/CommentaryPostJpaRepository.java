package com.cos.cercat.repository;

import com.cos.cercat.domain.CommentaryPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentaryPostJpaRepository extends JpaRepository<CommentaryPostEntity, Long>, PostJpaRepositoryCustom<CommentaryPostEntity> {

    @Query("""
        SELECT cp
        FROM CommentaryPostEntity cp
        WHERE cp.userEntity.id = :userId
        """)
    Slice<CommentaryPostEntity> findByUserId(Long userId, Pageable pageable);
}
