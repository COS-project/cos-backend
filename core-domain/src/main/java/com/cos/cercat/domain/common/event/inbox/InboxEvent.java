package com.cos.cercat.domain.common.event.inbox;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.EventStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InboxEvent {

    private final static int MAX_RETRIES = 3;

    private String id;
    private String eventType;
    private Event event;
    private int tryCount;
    private String lastErrorMessage;
    private EventStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime lastTryAt;
    private LocalDateTime processedAt;

    public static InboxEvent from(Event event) {
        return InboxEvent.builder()
                .id(event.resolveId())
                .event(event)
                .eventType(event.resolveType())
                .tryCount(0)
                .status(EventStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void retry(String errorMessage) {
        this.tryCount++;
        this.lastErrorMessage = errorMessage;
        this.lastTryAt = LocalDateTime.now();

        if (this.tryCount >= MAX_RETRIES) {
            this.status = EventStatus.FAILED;
        } else {
            this.status = EventStatus.PENDING;
        }
    }

    public InboxEvent markAsProcessed() {
        this.status = EventStatus.PROCESSED;
        this.processedAt = LocalDateTime.now();
        return this;
    }
}
