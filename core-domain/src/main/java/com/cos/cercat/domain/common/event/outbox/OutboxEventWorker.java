package com.cos.cercat.domain.common.event.outbox;

import com.cos.cercat.domain.common.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventWorker {

    private final EventPublisher eventPublisher;

    private final OutboxEventManager outboxEventManager;

    public void process() {
        outboxEventManager.readUnprocessedEvents()
                .parallelStream()
                .forEach(outboxEvent -> eventPublisher.publish(outboxEvent.getEvent()));
    }
}
