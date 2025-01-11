package com.cos.cercat.domain.alarm;

public interface EventSender {

    void send(AlarmEvent alarmEvent);

}
