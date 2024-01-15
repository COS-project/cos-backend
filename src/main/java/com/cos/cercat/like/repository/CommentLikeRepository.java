package com.cos.cercat.like.repository;

import com.cos.cercat.like.domain.CommentLike;
import com.cos.cercat.like.domain.EmbeddedId.CommentLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikePK> {
}
