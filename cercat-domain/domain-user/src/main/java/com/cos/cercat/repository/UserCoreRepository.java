package com.cos.cercat.repository;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.user.FindUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserRepository;
import org.springframework.stereotype.Repository;


@Repository
public class UserCoreRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserCoreRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User read(FindUser findUser) {
        return userJpaRepository.findById(findUser.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND))
                .toUser();
    }
}
