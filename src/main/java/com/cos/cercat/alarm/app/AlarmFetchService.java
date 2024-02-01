package com.cos.cercat.alarm.app;

import com.cos.cercat.alarm.dto.Response.AlarmResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmFetchService {

    private final AlarmService alarmService;
    private final UserService userService;

    public Page<AlarmResponse> getAlarms(Long userId, Pageable pageable) {
        User user = userService.getUser(userId);
        return alarmService.getAlarms(user, pageable).map(AlarmResponse::from);
    }

}
