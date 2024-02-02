package com.cos.cercat.alarm.app;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.alarm.dto.AlarmEvent;
import com.cos.cercat.alarm.repository.AlarmRepository;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
