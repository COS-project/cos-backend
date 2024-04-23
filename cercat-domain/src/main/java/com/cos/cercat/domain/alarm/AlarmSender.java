package com.cos.cercat.domain.alarm;

public interface AlarmSender {
    void send(AlarmEvent alarmEvent);
}
