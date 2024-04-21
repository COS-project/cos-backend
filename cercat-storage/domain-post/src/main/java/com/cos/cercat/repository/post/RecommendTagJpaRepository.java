package com.cos.cercat.repository.post;

import com.cos.cercat.domain.post.RecommendTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RecommendTagJpaRepository extends JpaRepository<RecommendTagEntity, Long> {

    @Query("select rt from RecommendTagEntity rt where rt.tipPost.id = :postId")
    Set<RecommendTagEntity> findByPostId(Long postId);
}
