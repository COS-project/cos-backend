package com.cos.cercat.domain.alarm;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOperationPublisher implements AlarmPublisher {

    @Override
    public void publish(AlarmEvent alarmEvent) {
        
    }
}
