package com.cos.cercat.alarm.api;

import com.cos.cercat.alarm.app.AlarmFetchService;
import com.cos.cercat.alarm.dto.Response.AlarmResponse;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "알림 조회 API")
public class AlarmFetchApi {

    private final AlarmFetchService alarmFetchService;

    @GetMapping("/alarms")
    @Operation(summary = "읽지 않은 알림 조회", description = "API 요청 시 읽음 처리")
    public Response<List<AlarmResponse>> getAlarmList(@AuthenticationPrincipal UserDTO userDTO) {
        return Response.success(alarmFetchService.getAlarms(userDTO.getId()));
    }

    @GetMapping("/alarms/unread")
    @Operation(summary = "읽지 않은 알림 수 조회")
    public Response<Long> countUnreadAlarm(@AuthenticationPrincipal UserDTO user) {
        return Response.success(alarmFetchService.countUnreadAlarm(user.getId()));
    }

}
