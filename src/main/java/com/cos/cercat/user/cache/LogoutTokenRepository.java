package com.cos.cercat.user.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@Slf4j
@RequiredArgsConstructor
public class LogoutTokenRepository {

    private final RedisTemplate<String, String> logoutRedisTemplate;

    public void setLogout(String email, String accessToken, long expiration) {
        String key = getLogoutKey(email);
        logoutRedisTemplate.opsForValue().set(key, accessToken, Duration.ofMillis(expiration));
    }

    public Boolean isLoginUser(String email) {
        String key = getLogoutKey(email);
        return logoutRedisTemplate.opsForValue().get(key) == null;
    }

    public String getLogoutKey(String email) {
        return "LOGOUT:" + email;
    }

}
