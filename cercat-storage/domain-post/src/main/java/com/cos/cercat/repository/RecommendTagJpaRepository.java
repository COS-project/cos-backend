package com.cos.cercat.repository;

import com.cos.cercat.domain.RecommendTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RecommendTagJpaRepository extends JpaRepository<RecommendTagEntity, Long> {

    @Query("select rt from RecommendTagEntity rt where rt.tipPost.id = :postId")
    Set<RecommendTagEntity> findByPostId(Long postId);

    @Modifying
    @Query("""
            delete from RecommendTagEntity rt
            where rt.tipPost.id = :postId
            """)
    void deleteByPostId(Long postId);
}
