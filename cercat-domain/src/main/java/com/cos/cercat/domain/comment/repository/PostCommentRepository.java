package com.cos.cercat.domain.comment.repository;

import com.cos.cercat.domain.comment.domain.PostComment;
import com.cos.cercat.domain.user.domain.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc from PostComment pc where pc.id = :id")
    Optional<PostComment> findByIdWithPessimisticLock(Long id);

    Slice<PostComment> findPostCommentsByUser(User user, Pageable pageable);
}
