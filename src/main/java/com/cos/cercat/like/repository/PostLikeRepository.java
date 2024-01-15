package com.cos.cercat.like.repository;

import com.cos.cercat.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.like.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {
}
