package com.cos.cercat.service;

import com.cos.cercat.domain.Alarm;
import com.cos.cercat.domain.UserEntity;
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

    public List<Alarm> getAlarms(UserEntity userEntity) {
        return alarmRepository.findAlarmsByReceiveUserEntityAndIsReadFalse(userEntity);
    }

    public void markAllAsRead(UserEntity userEntity) {
        alarmRepository.markAllAsReadByUserEntity(userEntity.getId());
    }

    public Long countUnreadAlarm(UserEntity userEntity) {
        return alarmRepository.countAlarmsByReceiveUserEntityAndIsReadFalse(userEntity);
    }


}
