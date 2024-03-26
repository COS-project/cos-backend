package com.cos.cercat.cache;


import com.cos.cercat.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, UserDTO> redisTemplate;
    private static final Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(UserDTO user) {
        String key = getKey(user.getEmail());
        log.info("Set User from {} : {}", key, user);
        redisTemplate.opsForValue().setIfAbsent(key, user, USER_CACHE_TTL);
    }

    public Optional<UserDTO> getUser(String email) {
        String key = getKey(email);
        UserDTO user = redisTemplate.opsForValue().get(key);
        log.info("Get User from {} : {}", key, user);
        return Optional.ofNullable(user);
    }

    public void deleteUser(String email) {
        String key = getKey(email);
        redisTemplate.delete(key);
        log.info("유저 캐싱 폐기 완료 - {}", key);
    }

    public String getKey(String email) {
        return "USER:" + email;
    }
}
