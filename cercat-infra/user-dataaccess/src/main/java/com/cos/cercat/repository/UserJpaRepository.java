package com.cos.cercat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cos.cercat.domain.UserEntity;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
