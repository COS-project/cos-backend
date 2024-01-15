package com.cos.cercat.comment.repository;

import com.cos.cercat.comment.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
