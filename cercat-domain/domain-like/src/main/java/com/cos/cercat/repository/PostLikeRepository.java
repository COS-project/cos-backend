package com.cos.cercat.repository;


import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {

    boolean existsPostLikeByPostLikePK(PostLikePK postLikePK);
}
