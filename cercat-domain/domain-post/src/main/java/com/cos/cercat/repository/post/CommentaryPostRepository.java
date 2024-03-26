package com.cos.cercat.repository.post;

import com.cos.cercat.domain.User;
import com.cos.cercat.domain.post.CommentaryPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryPostRepository extends JpaRepository<CommentaryPost, Long>, PostRepositoryCustom<CommentaryPost> {

    Slice<CommentaryPost> findCommentaryPostsByUser(User user, Pageable pageable);


}
