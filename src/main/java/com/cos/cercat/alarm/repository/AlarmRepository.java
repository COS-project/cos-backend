package com.cos.cercat.alarm.repository;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAlarmsByReceiveUserAndIsReadFalse(User receiveUser);

    // 특정 사용자의 모든 알림의 isRead 값을 true로 변경
    @Modifying
    @Query("UPDATE Alarm a SET a.isRead = true WHERE a.receiveUser.id = :userId")
    void markAllAsReadByUser(@Param("userId") Long userId);

    Long countAlarmsByReceiveUserAndIsReadFalse(User receiveUser);

}
