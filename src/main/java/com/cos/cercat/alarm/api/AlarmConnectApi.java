package com.cos.cercat.alarm.api;

import com.cos.cercat.alarm.app.SseEmitterService;
import com.cos.cercat.user.dto.UserDTO;
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
@Tag(name = "알람 생성 API")
public class AlarmConnectApi {

    private final SseEmitterService sseEmitterService;

    @GetMapping("/alarms/subscribe")
    public SseEmitter subscribeAlarm(@AuthenticationPrincipal UserDTO user) {
        return sseEmitterService.connect(user.getId());
    }
}
