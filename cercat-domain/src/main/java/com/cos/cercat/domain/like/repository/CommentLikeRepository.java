package com.cos.cercat.domain.like.repository;

import com.cos.cercat.domain.like.domain.CommentLike;
import com.cos.cercat.domain.like.domain.EmbeddedId.CommentLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikePK> {

    boolean existsCommentLikeByCommentLikePK(CommentLikePK commentLikePK);
}
