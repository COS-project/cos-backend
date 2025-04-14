package com.cos.cercat.domain.common.event.outbox;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.EventPublisher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {

    private final EventPublisher eventPublisher;
    private final ExecutorService asyncExecutor = Executors.newFixedThreadPool(4);


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessageHandler(Event event) {
        CompletableFuture.runAsync(() -> {
            eventPublisher.publish(event);
        }, asyncExecutor);
    }
}
