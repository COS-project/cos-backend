package com.cos.cercat.apis.alarm.app.usecase;

import com.cos.cercat.domain.alarm.service.AlarmService;
import com.cos.cercat.domain.alarm.service.SseEmitterService;
import com.cos.cercat.domain.alarm.domain.Alarm;
import com.cos.cercat.domain.alarm.dto.AlarmEvent;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.cos.cercat.domain.alarm.service.SseEmitterService.EVENT_NAME;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class AlarmCreateUseCase {

    private final AlarmService alarmService;
    private final SseEmitterService sseEmitterService;

    public void createAndSendAlarm(AlarmEvent event) {
        Alarm alarm = alarmService.save(event);
        log.info("receive user : {}, alarm type : {} 알람 생성", event.receiveUser(), event.alarmType());
        sseEmitterService.getEmitter(event.receiveUser().getId()).ifPresentOrElse(emitter -> {
            try {
                emitter.send(SseEmitter.event().id(alarm.getId().toString()).name(EVENT_NAME).data("new alarm"));
                log.info("emitter 전송 완료");
            } catch (IOException e) {
                sseEmitterService.delete(event.receiveUser().getId());
                throw new CustomException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        },
        () -> log.info("emitter not founded"));
    }

}
