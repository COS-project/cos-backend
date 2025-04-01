package com.cos.cercat.domain.common;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FailedEvent {

    private Long id;
    private final Event event;
    private final String eventType;
    private final String eventKey;
    private int retryCount;
    private String lastErrorMessage;
    private Status status;
    private final LocalDateTime createdAt;
    private LocalDateTime lastRetryAt;
    private LocalDateTime processedAt;

    public enum Status {
        PENDING,
        PROCESSED,
        FAILED
    }

    public static FailedEvent from(Event event, int retryCount, String errorMessage) {
        return FailedEvent.builder()
                .event(event)
                .eventType(event.resolveType())
                .eventKey(event.resolveKey())
                .retryCount(retryCount)
                .lastErrorMessage(errorMessage)
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void markAsProcessed() {
        this.status = Status.PROCESSED;
        this.processedAt = LocalDateTime.now();
    }

    public void markAsFailed(String errorMessage) {
        this.retryCount++;
        this.lastErrorMessage = errorMessage;
        this.status = Status.PENDING;
        this.lastRetryAt = LocalDateTime.now();
    }

    public void markAsPermanentlyFailed() {
        this.status = Status.FAILED;
        this.lastRetryAt = LocalDateTime.now();
    }

    public boolean isRetryable(int maxRetries) {
        return this.status == Status.PENDING && this.retryCount < maxRetries;
    }

    @Override
    public String toString() {
        return String.format("FailedEvent(id=%d, type=%s, key=%s, retryCount=%d, status=%s)",
                id, eventType, eventKey, retryCount, status);
    }
}
