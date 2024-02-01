package com.cos.cercat.user.app;

import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.global.util.JwtTokenUtil;
import com.cos.cercat.user.cache.LogoutTokenRepository;
import com.cos.cercat.user.cache.UserCacheRepository;
import com.cos.cercat.user.cache.TokenCacheRepository;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    private final TokenCacheRepository tokenCacheRepository;
    private final LogoutTokenRepository logoutTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public UserDTO findByEmail(String email) {
        UserDTO userDTO = userCacheRepository.getUser(email).orElseGet(() ->
                userRepository.findByEmail(email).map(UserDTO::fromEntity).orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        log.info("userDTO : {}", userDTO);
        return userDTO;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void logout(HttpServletRequest request, String email) {

        String accessToken = request.getHeader("Access-Token");

        tokenCacheRepository.deleteRefreshToken(accessToken);

        long accessTokenExpirationMillis = jwtTokenUtil.getAccessTokenExpirationMillis(accessToken);
        log.info("accessTokenExpirationMillis - {}", accessTokenExpirationMillis);
        logoutTokenRepository.setLogout(email, accessToken, accessTokenExpirationMillis);

    }

    public Boolean isLoginUser(String userEmail) {
        return logoutTokenRepository.isLoginUser(userEmail);
    }

//    private void refreshMember(UserDTO user) { 캐시 새로고침
//        memberCacheRepository.deleteUser(user.getEmail());
//        memberCacheRepository.setUser(user);
//    }

}
