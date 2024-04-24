package com.cos.cercat.sse;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.alarm.AlarmEvent;
import com.cos.cercat.domain.alarm.SseProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SseProcessorImpl implements SseProcessor {

    public final static String EVENT_NAME = "alarm";
    private final SseEmitterCacheRepository emitterRepository;

    public void save(Long userId, SseEmitter emitter) {
        emitterRepository.save(userId, emitter);
    }


    public void delete(Long userId) {
        emitterRepository.delete(userId);
    }

    @Override
    public void sendEvent(AlarmEvent event) {
        emitterRepository.getEmitter(event.recieveUser().id()).ifPresentOrElse(emitter -> {
                    try {
                        emitter.send(SseEmitter.event().id("").name(EVENT_NAME).data("new alarmEntity"));
                    } catch (IOException e) {
                        emitterRepository.delete(event.recieveUser().id());
                        throw new CustomException(ErrorCode.ALARM_CONNECT_ERROR);
                    }
                },
                () -> log.info("emitter not founded"));
    }

}
