package com.cos.cercat.alarm.app.facade;

import com.cos.cercat.alarm.app.AlarmService;
import com.cos.cercat.alarm.app.SseEmitterService;
import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.alarm.dto.AlarmEvent;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.cos.cercat.alarm.app.SseEmitterService.EVENT_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmCreateService {

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
