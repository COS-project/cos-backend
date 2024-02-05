package com.cos.cercat.comment.repository;

import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc from PostComment pc where pc.id = :id")
    Optional<PostComment> findByIdWithPessimisticLock(Long id);

    Page<PostComment> findPostCommentsByUser(User user, Pageable pageable);
}
