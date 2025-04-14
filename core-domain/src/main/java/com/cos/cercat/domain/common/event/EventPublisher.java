package com.cos.cercat.domain.common.event;

public interface EventPublisher {
    void publish(Event event);
}
