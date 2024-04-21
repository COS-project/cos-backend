package com.cos.cercat.repository;


import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeJpaRepository extends JpaRepository<PostLikeEntity, PostLikePK> {

    @Query("""
            select exists (select 1 from PostLikeEntity pl
            where pl.postLikePK.postId = :postId
            and pl.postLikePK.userId = :userId)
            """)
    boolean existsPostLikeByPostLikePK(Long postId, Long userId);
}
