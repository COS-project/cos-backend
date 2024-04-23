package com.cos.cercat.domain.alarm;

public interface SseProcessor {

    void sendEvent(AlarmEvent alarmEvent);

}
