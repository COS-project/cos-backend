package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.PostEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PostEntity p where p.id = :id")
    Optional<PostEntity> findByIdWithPessimisticLock(Long id);

}
