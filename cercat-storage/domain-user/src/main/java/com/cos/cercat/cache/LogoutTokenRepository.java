package com.cos.cercat.cache;

import com.nimbusds.jose.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class LogoutTokenRepository {

    private final RedisTemplate<String, String> logoutRedisTemplate;

    public void setLogout(String email, String accessToken, long expiration) {
        String key = getLogoutKey(email);
        logoutRedisTemplate.opsForValue().set(key, accessToken, Duration.ofMillis(expiration));
    }

    public Boolean isLoginUser(Pair<String, String> tokenAndEmail) {
        String key = getLogoutKey(tokenAndEmail.getRight());
        String banToken = logoutRedisTemplate.opsForValue().get(key);
        if (banToken == null) {
            return true;
        }

        return !Objects.equals(banToken.replace("Bearer", "").trim(), tokenAndEmail.getLeft());
    }

    public String getLogoutKey(String email) {
        return "LOGOUT:" + email;
    }

}
