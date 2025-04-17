package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.post.entity.PostCommentEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostCommentJpaRepository extends JpaRepository<PostCommentEntity, Long>, PostCommentCustomRepository {

    @Query("select pc from PostCommentEntity pc where pc.postId = :postId")
    List<PostCommentEntity> findByPostId(Long postId);

    @Query("""
            select pc from PostCommentEntity pc
            where pc.userEntity.id = :userId
            """)
    Slice<PostCommentEntity> findByUserId(Long userId, PageRequest pageRequest);
}
