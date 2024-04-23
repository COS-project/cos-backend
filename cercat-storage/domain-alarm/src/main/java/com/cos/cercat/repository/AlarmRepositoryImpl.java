package com.cos.cercat.repository;

import com.cos.cercat.domain.AlarmEntity;
import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.AlarmEvent;
import com.cos.cercat.domain.alarm.AlarmRepository;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;

    @Override
    public List<Alarm> findUnreadAlarms(TargetUser targetUser) {
        return alarmJpaRepository.findUnreadAlarms(targetUser.userId()).stream()
                .map(AlarmEntity::toDomain)
                .toList();
    }

    @Override
    public int countUnreadAlarms(TargetUser targetUser) {
        return alarmJpaRepository.countUnreadAlarms(targetUser.userId()).intValue();
    }

    @Override
    public void markAsRead(TargetUser targetUser) {
        alarmJpaRepository.markAllAsReadByUserEntity(targetUser.userId());
    }

    @Override
    public void save(AlarmEvent alarmEvent) {
        AlarmEntity alarmEntity = AlarmEntity.from(alarmEvent);
        alarmJpaRepository.save(alarmEntity);
    }
}
