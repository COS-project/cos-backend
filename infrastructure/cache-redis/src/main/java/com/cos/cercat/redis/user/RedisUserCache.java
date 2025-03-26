package com.cos.cercat.redis.user;

import static com.cos.cercat.redis.user.config.UserRedisConfig.USER_CACHE_TTL;

import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Slf4j
@RequiredArgsConstructor
public class RedisUserCache implements UserCache {

    private final RedisTemplate<String, User> redisTemplate;

    public void cache(User user) {
        String key = getKey(user.getId());
        log.debug("Set UserEntity from {} : {}", key, user.getUsername());
        redisTemplate.opsForValue().setIfAbsent(key, user, USER_CACHE_TTL);
    }

    public Optional<User> find(UserId userId) {
        String key = getKey(userId.userId());
        Optional<User> user = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        user.ifPresentOrElse(
                u -> log.debug("Get User from Cache - {} : {}", key, u.getUsername()),
                () -> log.debug("No User Cache - {}", key)
        );
        return user;
    }

    public void delete(User user) {
        String key = getKey(user.getId());
        redisTemplate.delete(key);
        log.debug("유저 캐싱 폐기 완료 - {}", key);
    }

    public String getKey(Long userId) {
        return "USER:" + userId;
    }
}
