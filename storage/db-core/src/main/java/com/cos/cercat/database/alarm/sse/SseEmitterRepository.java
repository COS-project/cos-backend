package com.cos.cercat.database.alarm.sse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Repository
@Slf4j
public class SseEmitterRepository {

    // thread-safe한 자료구조를 사용한다.
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Long userId, SseEmitter sseEmitter) {
        log.info("SseEmitter save: {}", userId);
        emitters.put(userId, sseEmitter);
    }

    public Optional<SseEmitter> findById(Long userId) {
        return Optional.ofNullable(emitters.get(userId));
    }

    public void delete(Long userId) {
        log.info("SseEmitter delete: {}", userId);
        emitters.remove(userId);
    }

}
