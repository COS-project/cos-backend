package com.cos.cercat.domain.common.event.outbox;

import com.cos.cercat.domain.common.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OutboxEventProcessor {

    private final EventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void process(OutboxEvent outboxEvent) {
        eventPublisher.publish(outboxEvent.getEvent());
    }

}
