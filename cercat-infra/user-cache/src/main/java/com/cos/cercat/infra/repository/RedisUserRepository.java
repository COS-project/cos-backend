package com.cos.cercat.infra.repository;

import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.cos.cercat.infra.config.UserRedisConfig.USER_CACHE_TTL;

@Repository
@Slf4j
@Profile("!batch")
@RequiredArgsConstructor
public class RedisUserRepository implements UserCacheRepository {

    private final RedisTemplate<String, User> redisTemplate;

    public void setUser(User user) {
        String key = getKey(user.getId());
        log.info("Set UserEntity from {} : {}", key, user.getUsername());
        redisTemplate.opsForValue().setIfAbsent(key, user, USER_CACHE_TTL);
    }

    public Optional<User> getUser(TargetUser targetUser) {
        String key = getKey(targetUser.userId());
        Optional<User> user = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        user.ifPresentOrElse(
                u -> log.info("Get User from Cache - {} : {}", key, u.getUsername()),
                () -> log.info("No User Cache - {}", key)
        );
        return user;
    }

    public void deleteUser(User user) {
        String key = getKey(user.getId());
        redisTemplate.delete(key);
        log.info("유저 캐싱 폐기 완료 - {}", key);
    }

    public String getKey(Long userId) {
        return "USER:" + userId;
    }
}
