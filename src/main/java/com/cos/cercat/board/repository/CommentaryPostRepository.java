package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.CommentaryPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryPostRepository extends JpaRepository<CommentaryPost, Long> {
}
