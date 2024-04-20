package com.cos.cercat.repository.post;

import com.cos.cercat.domain.post.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageJpaRepository extends JpaRepository<PostImageEntity, Long> {
}
