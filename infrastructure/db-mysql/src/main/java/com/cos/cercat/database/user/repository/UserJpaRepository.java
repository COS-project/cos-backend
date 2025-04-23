package com.cos.cercat.database.user.repository;

import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.user.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByOAuthProviderAndProviderId(OAuthProvider oAuthProvider, String providerId);
}
