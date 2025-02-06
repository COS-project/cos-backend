package com.cos.cercat.database.alarm.repository;

import com.cos.cercat.database.alarm.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {

    @Query("""
            SELECT a
            FROM AlarmEntity a
            WHERE a.receiver.id = :receiveUserId
            AND a.isRead = false
            """)
    List<AlarmEntity> findUnreadAlarms(Long receiveUserId);

    // 특정 사용자의 모든 알림의 isRead 값을 true로 변경
    @Modifying
    @Query("UPDATE AlarmEntity a SET a.isRead = true WHERE a.receiver.id IN :alarmIds")
    void markAllAsReadByIds(@Param("alarmIds") List<Long> alarmIds);


    @Query("""
            SELECT COUNT(a)
            FROM AlarmEntity a
            WHERE a.receiver.id = :receiveUserId
            AND a.isRead = false
            """)
    Long countUnreadAlarms(Long receiveUserId);

}
