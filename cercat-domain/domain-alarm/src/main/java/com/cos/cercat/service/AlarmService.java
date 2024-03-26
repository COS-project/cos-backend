package com.cos.cercat.service;

import com.cos.cercat.domain.Alarm;
import com.cos.cercat.domain.User;
import com.cos.cercat.dto.AlarmEvent;
import com.cos.cercat.repository.AlarmRepository;
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
