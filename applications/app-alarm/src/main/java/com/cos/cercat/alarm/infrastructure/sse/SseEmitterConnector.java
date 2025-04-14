package com.cos.cercat.alarm.infrastructure.sse;

import static com.cos.cercat.alarm.infrastructure.sse.SSESender.EVENT_NAME;

import com.cos.cercat.alarm.infrastructure.sse.exception.SseConnectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseEmitterConnector {

    //5분
    final static long DEFAULT_TIMEOUT = 300000L;
    private final SseEmitterRepository sseEmitterRepository;

    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        sseEmitterRepository.save(userId, emitter);
        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(() -> {
            sseEmitterRepository.delete(userId);
        });
        try {
            emitter.send(SseEmitter.event().id("").name(EVENT_NAME).data("connect completed"));
            log.info("SSE 연결 userId - {}", userId);
        } catch (IOException e) {
            throw SseConnectException.EXCEPTION;
        }
        return emitter;
    }
}
