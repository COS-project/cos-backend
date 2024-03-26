package com.cos.cercat.repository.post;

import com.cos.cercat.domain.User;
import com.cos.cercat.domain.post.NormalPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalPostRepository extends JpaRepository<NormalPost, Long> {

    Slice<NormalPost> findNormalPostsByUser(User user, Pageable pageable);

}
