package com.cos.cercat.domain.common.event.outbox;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.EventStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutboxEvent {

    private static final int MAX_RETRY_COUNT = 3;

    private String id;
    private final Event event;
    private final String eventType;
    private int tryCount;
    private String lastErrorMessage;
    private EventStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime lastTryAt;
    private LocalDateTime processedAt;

    public static OutboxEvent pending(Event event) {
        return OutboxEvent.builder()
                .id(event.resolveId())
                .event(event)
                .eventType(event.resolveType())
                .tryCount(0)
                .lastErrorMessage("")
                .status(EventStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public OutboxEvent retry(String errorMessage) {
        this.tryCount++;
        this.lastTryAt = LocalDateTime.now();
        this.lastErrorMessage = errorMessage;

        if(this.tryCount >= MAX_RETRY_COUNT) {
            this.status = EventStatus.FAILED;
        } else {
            this.status = EventStatus.PENDING;
        }
        return this;
    }

    public OutboxEvent markAsProcessed() {
        this.status = EventStatus.PROCESSED;
        this.processedAt = LocalDateTime.now();
        return this;
    }
}
