package com.cos.cercat.alarm.api;

import com.cos.cercat.alarm.app.AlarmFetchService;
import com.cos.cercat.alarm.dto.Response.AlarmResponse;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "알림 조회 API")
public class AlarmFetchApi {

    private final AlarmFetchService alarmFetchService;

    @GetMapping("/alarms")
    public Response<Page<AlarmResponse>> getAlarmList(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                      @AuthenticationPrincipal UserDTO userDTO) {
        return Response.success(alarmFetchService.getAlarms(userDTO.getId(), pageable));
    }

}
