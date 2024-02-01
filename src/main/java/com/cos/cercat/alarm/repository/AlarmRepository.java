package com.cos.cercat.alarm.repository;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findAlarmsByReceiveUser(User receiveUser, Pageable pageable);

}
