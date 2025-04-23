package com.cos.cercat.database.user.repository;

import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.user.Provider;
import com.cos.cercat.domain.user.exception.UserNotFoundException;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  @Override
  public User find(UserId userId) {
    return userJpaRepository.findById(userId.userId())
        .orElseThrow(() -> UserNotFoundException.EXCEPTION)
        .toDomain();
  }

  @Override
  public User save(User user) {
    return userJpaRepository.save(UserEntity.from(user)).toDomain();
  }

  @Override
  public void delete(User user) {
    userJpaRepository.delete(UserEntity.from(user));
  }

  @Override
  public User readByEmail(String email) {
    return userJpaRepository.findByEmail(email)
        .orElseThrow(() -> UserNotFoundException.EXCEPTION)
        .toDomain();
  }

  @Override
  public User readByProvider(Provider provider) {
    return userJpaRepository.findByoAuthProviderAndProviderId(provider.provider(), provider.providerId())
        .orElseThrow(() -> UserNotFoundException.EXCEPTION)
        .toDomain();
  }
}
