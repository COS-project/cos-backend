package com.cos.cercat.post.repository;

import com.cos.cercat.post.domain.CommentaryPost;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryPostRepository extends JpaRepository<CommentaryPost, Long>, PostRepositoryCustom<CommentaryPost> {

    Slice<CommentaryPost> findCommentaryPostsByUser(User user, Pageable pageable);
}
