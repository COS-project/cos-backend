package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.CommentaryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentaryPostRepository extends JpaRepository<CommentaryPost, Long>, PostRepositoryCustom<CommentaryPost> {

}
