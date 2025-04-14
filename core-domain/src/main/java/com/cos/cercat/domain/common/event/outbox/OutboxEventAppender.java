package com.cos.cercat.domain.common.event.outbox;

import com.cos.cercat.domain.common.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventAppender {

    private final OutboxEventManager outboxEventManager;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleOutboxEvent(Event event) {
        log.debug("Handling outbox event: {}", event);
        outboxEventManager.append(OutboxEvent.pending(event));
    }

}
