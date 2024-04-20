package com.cos.cercat.service;

import com.cos.cercat.repository.SseEmitterCacheRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class SseEmitterService {

    public final static String EVENT_NAME = "alarm";
    private final SseEmitterCacheRepository emitterRepository;

    public SseEmitter connect(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(emitterRepository.DEFAULT_TIMEOUT);

        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));
        try {
            sseEmitter.send(SseEmitter.event().id("").name(EVENT_NAME).data("connect completed"));
            log.info("SSE 연결 userId - {}", userId);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;
    }

    public void delete(Long userId) {
        emitterRepository.delete(userId);
    }

    public Optional<SseEmitter> getEmitter(Long userId) {
        return emitterRepository.getEmitter(userId);
    }

}
