package com.cos.cercat.apis.alarm.app.usecase;

import com.cos.cercat.domain.alarm.service.AlarmService;
import com.cos.cercat.apis.alarm.dto.Response.AlarmResponse;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.user.service.UserService;
import com.cos.cercat.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AlarmFetchUseCase {

    private final AlarmService alarmService;
    private final UserService userService;

    @Transactional
    public List<AlarmResponse> getAlarms(Long userId) {
        User user = userService.getUser(userId);

        List<AlarmResponse> alarmResponses = alarmService.getAlarms(user).stream().map(AlarmResponse::from).toList();
        alarmService.markAllAsRead(user); //안읽은 모든 알림을 읽음으로 업데이트
        log.info("user - {} 읽지 않은 알림 조회 ", user.getEmail());

        return alarmResponses;
    }

    public Long countUnreadAlarm(Long userId) {
        User user = userService.getUser(userId);
        log.info("user - {} 읽지 않은 알림 수 조회", user.getEmail());
        return alarmService.countUnreadAlarm(user);
    }

}
