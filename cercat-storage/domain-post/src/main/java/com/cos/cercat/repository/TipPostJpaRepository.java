package com.cos.cercat.repository;

import com.cos.cercat.domain.TipPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipPostJpaRepository extends JpaRepository<TipPostEntity, Long> {

    @Query("""
        SELECT tp
        FROM TipPostEntity tp
        WHERE tp.certificateEntity.id = :certificateId
        ORDER BY tp.likeCount DESC
        LIMIT 3
        """)
    List<TipPostEntity> findTop3(Long certificateId);

    @Query("""
        SELECT tp
        FROM TipPostEntity tp
        WHERE tp.userEntity.id = :userId
        """)
    Slice<TipPostEntity> findTipPostsByUserId(Long userId, Pageable pageable);

}
