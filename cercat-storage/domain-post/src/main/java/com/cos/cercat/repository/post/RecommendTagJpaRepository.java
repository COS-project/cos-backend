package com.cos.cercat.repository.post;

import com.cos.cercat.domain.post.RecommendTag;
import com.cos.cercat.domain.post.RecommendTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendTagJpaRepository extends JpaRepository<RecommendTagEntity, Long> {

}
