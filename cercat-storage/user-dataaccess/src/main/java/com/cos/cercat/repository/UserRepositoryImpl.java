package com.cos.cercat.repository;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.exception.UserNotFoundException;
import com.cos.cercat.user.UserInfo;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User read(TargetUser targetUser) {
        return userJpaRepository.findById(targetUser.userId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION)
                .toDomain();
    }

    @Override
    public User update(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(UserEntity.from(user));
    }

    @Override
    public User save(UserInfo userInfo) {
        return userJpaRepository.save(UserEntity.from(userInfo)).toDomain();
    }

    @Override
    public User readBy(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION)
                .toDomain();
    }
}
