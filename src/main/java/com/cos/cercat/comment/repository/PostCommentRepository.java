package com.cos.cercat.comment.repository;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.comment.domain.PostComment;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc from PostComment pc where pc.id = :id")
    Optional<PostComment> findByIdWithPessimisticLock(Long id);
}
