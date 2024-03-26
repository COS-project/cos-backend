package com.cos.cercat.apis.alarm.api;

import com.cos.cercat.dto.UserDTO;
import com.cos.cercat.service.SseEmitterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "알람(SSE) 연결 API")
public class AlarmConnectApi {

    private final SseEmitterService sseEmitterService;

    @GetMapping("/alarms/subscribe")
    @Operation(summary = "SSE 연결 엔드포인트")
    public SseEmitter subscribeAlarm(@AuthenticationPrincipal UserDTO user) {
        return sseEmitterService.connect(user.getId());
    }
}
