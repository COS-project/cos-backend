package com.cos.cercat.alarm.sse;

import com.cos.cercat.domain.alarm.Alarm;

public interface AlarmBroadcaster {
    void broadcast(Alarm alarm);
}
