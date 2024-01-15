package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.TipPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipPostRepository extends JpaRepository<TipPost, Long>, PostRepositoryCustom<TipPost> {
}
