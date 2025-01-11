package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.post.entity.PostImageEntity;
import com.cos.cercat.database.post.entity.embeddedId.PostImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostImageJpaRepository extends JpaRepository<PostImageEntity, PostImageId> {

    @Query("SELECT p FROM PostImageEntity p WHERE p.postEntity.id = :postId")
    List<PostImageEntity> findByPostId(Long postId);

    @Modifying
    @Query("""
        delete from PostImageEntity p
        where p.imageEntity.id IN (:removeImageIds)
        """)
    void deleteAllByImageId(List<Long> removeImageIds);
}
