package com.cos.cercat.domain.alarm.service;

import com.cos.cercat.domain.alarm.domain.Alarm;
import com.cos.cercat.domain.alarm.dto.AlarmEvent;
import com.cos.cercat.domain.alarm.repository.AlarmRepository;
import com.cos.cercat.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public Alarm save(AlarmEvent event) {
        return alarmRepository.save(event.toEntity());
    }

    public List<Alarm> getAlarms(User user) {
        return alarmRepository.findAlarmsByReceiveUserAndIsReadFalse(user);
    }

    public void markAllAsRead(User user) {
        alarmRepository.markAllAsReadByUser(user.getId());
    }

    public Long countUnreadAlarm(User user) {
        return alarmRepository.countAlarmsByReceiveUserAndIsReadFalse(user);
    }


}
