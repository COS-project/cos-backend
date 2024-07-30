package com.cos.cercat.apis.alarm.sse;

import com.cos.cercat.domain.alarm.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Component
@RequiredArgsConstructor
@Slf4j
public class SseEmitterManager {

    private final SseEmitterRepository sseEmitterRepository;

    public void append(Long userId, SseEmitter sseEmitter) {
        sseEmitterRepository.save(userId, sseEmitter);
    }

    public SseEmitter read(AlarmEvent event) {
        return sseEmitterRepository.findById(event.recieveUser().getId()).orElseGet(() -> {
            log.info("SseEmitter not found: {}", event.recieveUser().getId());
            return null;
        });
    }

    public void delete(Long userId) {
        sseEmitterRepository.delete(userId);
    }

}
