package com.cos.cercat.repository;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.user.NewUser;
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
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND))
                .toDomain();
    }

    @Override
    public void update(User user) {
        userJpaRepository.save(UserEntity.from(user));
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(UserEntity.from(user));
    }

    @Override
    public User save(NewUser newUser) {
        return userJpaRepository.save(UserEntity.from(newUser)).toDomain();
    }

    @Override
    public User readBy(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND))
                .toDomain();
    }
}
