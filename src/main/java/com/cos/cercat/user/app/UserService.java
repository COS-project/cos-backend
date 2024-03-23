package com.cos.cercat.user.app;

import com.cos.cercat.global.common.Image;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.global.util.JwtTokenUtil;
import com.cos.cercat.search.cache.SearchLog;
import com.cos.cercat.search.cache.SearchLogCacheRepository;
import com.cos.cercat.user.cache.*;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.request.SearchLogDeleteRequest;
import com.cos.cercat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    private final TokenCacheRepository tokenCacheRepository;
    private final LogoutTokenRepository logoutTokenRepository;
    private final SearchLogCacheRepository searchLogCacheRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Cacheable(cacheNames = "USER", key = "#email")
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::fromEntity)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
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

    public Boolean isLoginUser(String userEmail) {
        return logoutTokenRepository.isLoginUser(userEmail);
    }

    public void refreshUserCache(User user) {
        log.info("user - {} 유저 캐시 리프레시", user.getEmail());
        userCacheRepository.deleteUser(user.getEmail());
        userCacheRepository.setUser(UserDTO.fromEntity(user));
    }
}
