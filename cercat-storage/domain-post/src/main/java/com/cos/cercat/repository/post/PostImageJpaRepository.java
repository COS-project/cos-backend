package com.cos.cercat.repository.post;

import com.cos.cercat.domain.post.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostImageJpaRepository extends JpaRepository<PostImageEntity, Long> {

    @Query("SELECT p FROM PostImageEntity p WHERE p.postEntity.id = :postId")
    List<PostImageEntity> findByPostId(Long postId);
}
