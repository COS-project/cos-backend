package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.NormalPost;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalPostRepository extends JpaRepository<NormalPost, Long>, PostRepositoryCustom<NormalPost> {

    Page<NormalPost> findNormalPostsByUser(User user, Pageable pageable);

}
