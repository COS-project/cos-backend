package com.cos.cercat.sse;

import com.cos.cercat.alarm.AlarmEvent;
import com.cos.cercat.alarm.SseClosedEvent;
import com.cos.cercat.alarm.SseProcessor;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SseProcessorImpl implements SseProcessor {

    public final static String EVENT_NAME = "alarm";
    private final SseEmitterManager sseEmitterManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void sendEvent(AlarmEvent event) {

        SseEmitter emitter = sseEmitterManager.read(event);

        if (emitter == null) {
            return;
        }

        try {
            log.info("sendEvent: {}", event);
            emitter.send(SseEmitter.event().id("").name(EVENT_NAME).data(event));
        } catch (IOException e) {
            sseEmitterManager.delete(event.recieveUser().getId());
            applicationEventPublisher.publishEvent(SseClosedEvent.from(event.recieveUser().getId()));
            throw new CustomException(ErrorCode.ALARM_SEND_ERROR);
        }
    }

}
