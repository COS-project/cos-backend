package com.cos.cercat.repository;

import com.cos.cercat.domain.PostEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PostEntity p where p.id = :id")
    Optional<PostEntity> findByIdWithPessimisticLock(Long id);

}
