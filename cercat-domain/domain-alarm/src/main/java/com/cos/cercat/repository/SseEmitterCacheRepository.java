package com.cos.cercat.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class SseEmitterCacheRepository {

    private final RedisTemplate<String, SseEmitter> sseEmitterRedisTemplate;
    public final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public void save(Long userId, SseEmitter emitter) {
        String key = getKey(userId);
        log.info("save Emitter {} : {}", key, emitter);

        sseEmitterRedisTemplate.opsForValue().set(key, emitter, DEFAULT_TIMEOUT);
    }

    public Optional<SseEmitter> getEmitter(Long userId) {
        String key = getKey(userId);
        SseEmitter sseEmitter = sseEmitterRedisTemplate.opsForValue().get(key);
        log.info("get Emitter {} : {}", key, sseEmitter);
        return Optional.ofNullable(sseEmitter);
    }

    public void delete(Long userId) {
        sseEmitterRedisTemplate.delete(getKey(userId));
        log.info("delete Emitter {}", getKey(userId));
    }

    private String getKey(Long userId) {
        return "emitter:UID - " + userId;
    }

}
