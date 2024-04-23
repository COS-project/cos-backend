package com.cos.cercat.service;

import com.cos.cercat.cache.LogoutTokenRepository;
import com.cos.cercat.cache.TokenCacheRepository;
import com.cos.cercat.cache.UserCacheRepository;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.entity.ImageEntity;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.dto.UserDTO;
import com.cos.cercat.repository.UserJpaRepository;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserCacheRepository userCacheRepository;
    private final TokenCacheRepository tokenCacheRepository;
    private final LogoutTokenRepository logoutTokenRepository;

    public UserDTO findByEmail(String email) {
        return userCacheRepository.getUser(email).orElseGet(() ->
                userJpaRepository.findByEmail(email).map(UserDTO::fromEntity).orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserEntity getUser(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void updateUser(Long userId, String nickName, ImageEntity imageEntity) {
        UserEntity userEntity = getUser(userId);
        userEntity.createUserInfo(nickName, imageEntity);
        refreshUserCache(userEntity);
        log.info("userEntity - {} 프로필 수정", userEntity.getEmail());
    }

    public void logout(String accessToken, String email, long accessTokenExpirationMillis) {
        tokenCacheRepository.deleteRefreshToken(accessToken);
        log.info("accessTokenExpirationMillis - {}", accessTokenExpirationMillis);
        logoutTokenRepository.setLogout(email, accessToken, accessTokenExpirationMillis);
    }

    public void deleteUser(Long userId) {
        userJpaRepository.deleteById(userId);
    }

    public Boolean isLoginUser(Pair<String, String> tokenAndEmail) {
        return logoutTokenRepository.isLoginUser(tokenAndEmail);
    }

    public void refreshUserCache(UserEntity userEntity) {
        log.info("userEntity - {} 유저 캐시 리프레시", userEntity.getEmail());
        userCacheRepository.deleteUser(userEntity.getEmail());
        userCacheRepository.setUser(UserDTO.fromEntity(userEntity));
    }
}
