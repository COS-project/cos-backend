package com.cos.cercat.sse;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.cos.cercat.sse.SseEmitterCacheRepository.DEFAULT_TIMEOUT;
import static com.cos.cercat.sse.SseProcessorImpl.EVENT_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class SseEmitterService {

    private final SseProcessorImpl sseProcessor;

    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        sseProcessor.save(userId, emitter);
        emitter.onCompletion(() -> sseProcessor.delete(userId));
        emitter.onTimeout(() -> sseProcessor.delete(userId));
        try {
            emitter.send(SseEmitter.event().id("").name(EVENT_NAME).data("connect completed"));
            log.info("SSE 연결 userId - {}", userId);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return emitter;
    }
}
