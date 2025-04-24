package com.cos.cercat.alarm.api;

import com.cos.cercat.alarm.infrastructure.sse.SseEmitterConnector;
import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.AlarmService;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlarmApi implements AlarmApiDocs {

    private final AlarmService alarmService;
    private final SseEmitterConnector sseEmitterConnector;

    @GetMapping(value = "/alarms/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribeAlarm(@AuthenticationPrincipal User currentUser) {
        SseEmitter connected = sseEmitterConnector.connect(currentUser.getId());
        return ResponseEntity.ok(connected);
    }

    @GetMapping("/alarms")
    public Response<List<Alarm>> getAlarmList(@AuthenticationPrincipal User currentUser) {
        return Response.success(alarmService.readAlarms(UserId.from(currentUser.getId())));
    }

    @GetMapping("/alarms/unread")
    public Response<Integer> countUnreadAlarm(@AuthenticationPrincipal User currentUser) {
        return Response.success(
                alarmService.countUnreadAlarms(UserId.from(currentUser.getId())));
    }

    @PostMapping("/alarms/read")
    public Response<Void> readAlarm(@RequestBody List<Long> alarmIds) {
        alarmService.markAsRead(alarmIds);
        return Response.success();
    }

}
