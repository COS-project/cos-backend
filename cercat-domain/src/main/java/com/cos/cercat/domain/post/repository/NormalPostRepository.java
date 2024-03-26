package com.cos.cercat.domain.post.repository;

import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.post.domain.NormalPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalPostRepository extends JpaRepository<NormalPost, Long> {

    Slice<NormalPost> findNormalPostsByUser(User user, Pageable pageable);

}
