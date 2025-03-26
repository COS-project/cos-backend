package com.cos.cercat.redis.user;

import static com.cos.cercat.redis.user.config.UserRedisConfig.BAN_TOKEN_KEY;

import com.cos.cercat.domain.user.RefreshToken;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.TokenCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Slf4j
@RequiredArgsConstructor
public class RedisTokenCache implements TokenCache {

    private final StringRedisTemplate banTokenRedisTemplate;
    private final RedisTemplate<String, RefreshToken> tokenRedisTemplate;

    @Override
    public void cacheRefreshToken(RefreshToken refreshToken) {
        String key = getKey(refreshToken.userId());
        log.info("Set Refresh Token from {} : {}", key, refreshToken);
        tokenRedisTemplate.opsForValue().set(key, refreshToken);
    }

    @Override
    public Optional<RefreshToken> find(UserId userId) {
        String key = getKey(userId.userId());
        RefreshToken token = tokenRedisTemplate.opsForValue().get(key);
        log.info("Get Refresh Token from {} : {}", key, token);
        return Optional.ofNullable(token);
    }

    @Override
    public void cacheAccessToken(String accessToken) {
        banTokenRedisTemplate.opsForSet().add(BAN_TOKEN_KEY, accessToken);
    }

    @Override
    public boolean exists(String token) {
        return Boolean.FALSE.equals(
                banTokenRedisTemplate.opsForSet().isMember(BAN_TOKEN_KEY, token));
    }

    public String getKey(Long userId) {
        return "REFRESH_TOKEN:" + userId;
    }
}
