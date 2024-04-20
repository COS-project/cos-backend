package com.cos.cercat.repository.post;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.CommentaryPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryPostJpaRepository extends JpaRepository<CommentaryPostEntity, Long>, PostJpaRepositoryCustom<CommentaryPostEntity> {

    Slice<CommentaryPostEntity> findCommentaryPostsByUserEntity(UserEntity userEntity, Pageable pageable);
}
