package com.cos.cercat.apis.alarm.api;

import com.cos.cercat.alarm.AlarmService;
import com.cos.cercat.apis.alarm.Response.AlarmResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.sse.SseEmitterService;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class AlarmApi implements AlarmApiDocs {

    private final AlarmService alarmService;
    private final SseEmitterService sseEmitterService;

    @GetMapping(value = "/alarms/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Response<SseEmitter> subscribeAlarm(@AuthenticationPrincipal User currentUser) {
        return Response.success(sseEmitterService.connect(currentUser.getId()));
    }

    @GetMapping("/alarms")
    public Response<List<AlarmResponse>> getAlarmList(@AuthenticationPrincipal User currentUser) {
        List<AlarmResponse> alarmResponses = alarmService.readAlarms(TargetUser.from(currentUser.getId())).stream()
                .map(AlarmResponse::from)
                .toList();
        return Response.success(alarmResponses);
    }

    @GetMapping("/alarms/unread")
    public Response<Integer> countUnreadAlarm(@AuthenticationPrincipal User currentUser) {
        return Response.success(alarmService.countUnreadAlarms(TargetUser.from(currentUser.getId())));
    }

}
