package com.cos.cercat.service;

import com.cos.cercat.cache.LogoutTokenRepository;
import com.cos.cercat.cache.TokenCacheRepository;
import com.cos.cercat.cache.UserCacheRepository;
import com.cos.cercat.entity.Image;
import com.cos.cercat.common.util.JwtTokenUtil;
import com.cos.cercat.domain.User;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.dto.UserDTO;;
import com.cos.cercat.repository.UserRepository;
import com.nimbusds.jose.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    private final TokenCacheRepository tokenCacheRepository;
    private final LogoutTokenRepository logoutTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UserDTO findByEmail(String email) {
        return userCacheRepository.getUser(email).orElseGet(() ->
                userRepository.findByEmail(email).map(UserDTO::fromEntity).orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateUser(Long userId, String nickName, Image image) {
        User user = getUser(userId);
        user.createUserInfo(nickName, image);
        refreshUserCache(user);
        log.info("user - {} 프로필 수정", user.getEmail());
    }

    public void logout(String accessToken, String email) {
        tokenCacheRepository.deleteRefreshToken(accessToken);

        long accessTokenExpirationMillis = jwtTokenUtil.getAccessTokenExpirationMillis(accessToken);
        log.info("accessTokenExpirationMillis - {}", accessTokenExpirationMillis);
        logoutTokenRepository.setLogout(email, accessToken, accessTokenExpirationMillis);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Boolean isLoginUser(Pair<String, String> tokenAndEmail) {
        return logoutTokenRepository.isLoginUser(tokenAndEmail);
    }

    public void refreshUserCache(User user) {
        log.info("user - {} 유저 캐시 리프레시", user.getEmail());
        userCacheRepository.deleteUser(user.getEmail());
        userCacheRepository.setUser(UserDTO.fromEntity(user));
    }
}
