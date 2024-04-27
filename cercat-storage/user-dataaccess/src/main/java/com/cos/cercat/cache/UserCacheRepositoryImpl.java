package com.cos.cercat.cache;

import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserCacheRepositoryImpl implements UserCacheRepository {

    private final RedisTemplate<String, User> redisTemplate;
    private static final Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(User user) {
        String key = getKey(user.getId());
        log.info("Set UserEntity from {} : {}", key, user);
        redisTemplate.opsForValue().setIfAbsent(key, user, USER_CACHE_TTL);
    }

    public Optional<User> getUser(TargetUser targetUser) {
        String key = getKey(targetUser.userId());
        User user = redisTemplate.opsForValue().get(key);
        log.info("Get UserEntity from {} : {}", key, user);
        return Optional.ofNullable(user);
    }

    public void deleteUser(TargetUser targetUser) {
        String key = getKey(targetUser.userId());
        redisTemplate.delete(key);
        log.info("유저 캐싱 폐기 완료 - {}", key);
    }

    public String getKey(Long userId) {
        return "USER:" + userId;
    }
}
