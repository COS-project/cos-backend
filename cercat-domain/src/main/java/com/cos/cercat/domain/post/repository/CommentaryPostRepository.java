package com.cos.cercat.domain.post.repository;

import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.post.domain.CommentaryPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryPostRepository extends JpaRepository<CommentaryPost, Long>, PostRepositoryCustom<CommentaryPost> {

    Slice<CommentaryPost> findCommentaryPostsByUser(User user, Pageable pageable);


}
