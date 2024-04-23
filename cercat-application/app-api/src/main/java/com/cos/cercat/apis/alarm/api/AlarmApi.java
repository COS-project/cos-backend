package com.cos.cercat.apis.alarm.api;

import com.cos.cercat.apis.alarm.Response.AlarmResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.alarm.AlarmService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import com.cos.cercat.sse.SseEmitterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "알림 API")
public class AlarmApi {

    private final AlarmService alarmService;
    private final SseEmitterService sseEmitterService;

    @GetMapping("/alarms/subscribe")
    @Operation(summary = "SSE 연결 엔드포인트")
    public SseEmitter subscribeAlarm(@AuthenticationPrincipal UserDTO currentUser) {
        return sseEmitterService.connect(currentUser.getId());
    }

    @GetMapping("/alarms")
    @Operation(summary = "읽지 않은 알림 조회", description = "API 요청 시 읽음 처리")
    public Response<List<AlarmResponse>> getAlarmList(@AuthenticationPrincipal UserDTO currentUser) {
        List<AlarmResponse> alarmResponses = alarmService.readAlarms(TargetUser.from(currentUser.getId())).stream()
                .map(AlarmResponse::from)
                .toList();
        return Response.success(alarmResponses);
    }

    @GetMapping("/alarms/unread")
    @Operation(summary = "읽지 않은 알림 수 조회")
    public Response<Integer> countUnreadAlarm(@AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(alarmService.countUnreadAlarms(TargetUser.from(currentUser.getId())));
    }

}
