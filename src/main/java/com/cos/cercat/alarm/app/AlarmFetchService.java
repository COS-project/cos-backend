package com.cos.cercat.alarm.app;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.alarm.dto.Response.AlarmResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmFetchService {

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
