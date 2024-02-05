package com.cos.cercat.post.repository;

import com.cos.cercat.post.domain.NormalPost;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalPostRepository extends JpaRepository<NormalPost, Long>, PostRepositoryCustom<NormalPost> {

    Slice<NormalPost> findNormalPostsByUser(User user, Pageable pageable);

}
