package com.cos.cercat.repository;

import com.cos.cercat.domain.CommentLike;
import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikePK> {

    boolean existsCommentLikeByCommentLikePK(CommentLikePK commentLikePK);
}
