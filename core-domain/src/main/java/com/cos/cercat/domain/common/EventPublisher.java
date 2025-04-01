package com.cos.cercat.domain.common;

public interface EventPublisher {
    void publish(Event event);

    void retry(Event event, int retryCount);
}
