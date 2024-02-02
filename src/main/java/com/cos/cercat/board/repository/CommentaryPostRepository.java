package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentaryPostRepository extends JpaRepository<CommentaryPost, Long>, PostRepositoryCustom<CommentaryPost> {

    Slice<CommentaryPost> findCommentaryPostsByUser(User user, Pageable pageable);
}
