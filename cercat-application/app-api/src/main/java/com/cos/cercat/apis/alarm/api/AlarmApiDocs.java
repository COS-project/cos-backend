package com.cos.cercat.apis.alarm.api;

import com.cos.cercat.apis.alarm.response.AlarmResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Tag(name = "알림 API")
public interface AlarmApiDocs {

    @Operation(summary = "SSE 연결 엔드포인트")
    ResponseEntity<SseEmitter> subscribeAlarm(@AuthenticationPrincipal User currentUser);

    @Operation(summary = "읽지 않은 알림 조회", description = "API 요청 시 읽음 처리")
    Response<List<AlarmResponse>> getAlarmList(@AuthenticationPrincipal User currentUser);

    @Operation(summary = "읽지 않은 알림 수 조회")
    Response<Integer> countUnreadAlarm(@AuthenticationPrincipal User currentUser);
}
