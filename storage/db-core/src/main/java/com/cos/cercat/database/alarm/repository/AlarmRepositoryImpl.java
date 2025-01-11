package com.cos.cercat.database.alarm.repository;

import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.AlarmEvent;
import com.cos.cercat.domain.alarm.AlarmRepository;
import com.cos.cercat.database.alarm.entity.AlarmEntity;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;

    @Override
    public List<Alarm> findUnreadAlarms(User user) {
        return alarmJpaRepository.findUnreadAlarms(user.getId()).stream()
                .map(AlarmEntity::toDomain)
                .toList();
    }

    @Override
    public int countUnreadAlarms(User user) {
        return alarmJpaRepository.countUnreadAlarms(user.getId()).intValue();
    }

    @Override
    public void markAsRead(List<Long> alarmIds) {
        alarmJpaRepository.markAllAsReadByIds(alarmIds);
    }

    @Override
    public void save(AlarmEvent alarmEvent) {
        AlarmEntity alarmEntity = AlarmEntity.from(alarmEvent);
        alarmJpaRepository.save(alarmEntity);
    }
}
