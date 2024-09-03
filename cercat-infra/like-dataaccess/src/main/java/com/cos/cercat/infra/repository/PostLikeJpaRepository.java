package com.cos.cercat.infra.repository;


import com.cos.cercat.infra.entity.EmbeddedId.PostLikePK;
import com.cos.cercat.infra.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeJpaRepository extends JpaRepository<PostLikeEntity, PostLikePK> {

    @Query("""
            select exists (select 1 from PostLikeEntity pl
            where pl.postLikePK.postId = :postId
            and pl.postLikePK.userId = :userId)
            """)
    boolean existsPostLikeByPostLikePK(Long userId, Long postId);

    @Modifying
    @Query("""
            DELETE FROM PostLikeEntity pl
            WHERE pl.postLikePK.userId = :userId
            AND pl.postLikePK.postId = :postId
            """)
    void deleteByPostIdAndUserId(Long userId, Long postId);
}
