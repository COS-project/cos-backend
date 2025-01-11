package com.cos.cercat.domain.alarm;

public interface AlarmPublisher {

    void publish(AlarmEvent alarmEvent);
}
