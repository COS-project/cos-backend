package com.cos.cercat.repository;

import com.cos.cercat.domain.CommentLikeEntity;
import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, CommentLikePK> {

    @Query("""
            select exists (select 1 from CommentLikeEntity cl
            where cl.commentLikePK.commentId = :commentId
            and cl.commentLikePK.userId = :userId)
            """)
    boolean existsCommentLikeByCommentLikePK(Long userId, Long commentId);

    @Modifying
    @Query("""
        DELETE FROM CommentLikeEntity cl
        WHERE cl.commentLikePK.userId = :userId
        AND cl.commentLikePK.commentId = :commentId
        """)
    void deleteByCommentIdAndUserId(Long userId, Long commentId);
}
