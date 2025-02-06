package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.post.entity.RecommendTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RecommendTagJpaRepository extends JpaRepository<RecommendTagEntity, Long> {

    @Query("select rt from RecommendTagEntity rt where rt.postId = :postId")
    Set<RecommendTagEntity> findByPostId(Long postId);

    @Modifying
    @Query("""
            delete from RecommendTagEntity rt
            where rt.postId = :postId
            """)
    void deleteByPostId(Long postId);
}
