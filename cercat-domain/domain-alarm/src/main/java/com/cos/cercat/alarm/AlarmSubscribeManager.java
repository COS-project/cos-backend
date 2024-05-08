package com.cos.cercat.alarm;

public interface AlarmSubscribeManager {
    void subscribe(Long userId);
    void unsubscribe(Long userId);
}
