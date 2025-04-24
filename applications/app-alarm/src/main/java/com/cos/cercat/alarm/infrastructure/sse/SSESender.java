package com.cos.cercat.alarm.infrastructure.sse;

import com.cos.cercat.alarm.infrastructure.sse.exception.AlarmSendException;
import com.cos.cercat.domain.alarm.Alarm;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@RequiredArgsConstructor
public class SSESender {

    public final static String EVENT_NAME = "alarm";
    private final SseEmitterRepository sseEmitterRepository;

    public void send(Long receiverId, String alarm) {
        Optional<SseEmitter> emitter = sseEmitterRepository.findById(receiverId);

        if (emitter.isEmpty()) {
            return;
        }

        try {
            emitter.get().send(SseEmitter.event()
                    .name(EVENT_NAME)
                    .data(alarm)
            );
        } catch (IOException e) {
            sseEmitterRepository.delete(receiverId);
            throw AlarmSendException.EXCEPTION;
        }
    }
}
