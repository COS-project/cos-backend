package com.cos.cercat.domain.like.repository;


import com.cos.cercat.domain.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.like.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {

    boolean existsPostLikeByPostLikePK(PostLikePK postLikePK);
}
