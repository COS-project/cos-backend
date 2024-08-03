package com.cos.cercat.sse;

import com.cos.cercat.sse.exception.SseConnectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.cos.cercat.sse.SseEventSender.EVENT_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class SseEmitterConnector {
    
    final static long DEFAULT_TIMEOUT = 60 * 500L;
    private final SseEmitterManager sseEmitterManager;

    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        sseEmitterManager.append(userId, emitter);
        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(() -> {
            sseEmitterManager.delete(userId);
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
