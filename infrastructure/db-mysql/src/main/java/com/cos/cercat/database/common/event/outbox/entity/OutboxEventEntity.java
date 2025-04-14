package com.cos.cercat.database.common.event.outbox.entity;

import static com.cos.cercat.database.common.event.EventPayloadConverter.*;

import com.cos.cercat.domain.common.event.EventStatus;
import com.cos.cercat.domain.common.event.outbox.OutboxEvent;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "outbox_events")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class OutboxEventEntity {

    @Id
    private String id;
    private String eventType;
    @Column(columnDefinition = "TEXT")
    private String payload;
    private Integer tryCount;
    @Column(columnDefinition = "TEXT")
    private String lastErrorMessage;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime lastTryAt;
    private LocalDateTime processedAt;

    public static OutboxEventEntity from(OutboxEvent outboxEvent) {
        return OutboxEventEntity.builder()
                .id(outboxEvent.getId())
                .eventType(outboxEvent.getEventType())
                .payload(serialize(outboxEvent.getEvent()))
                .tryCount(outboxEvent.getTryCount())
                .lastErrorMessage(outboxEvent.getLastErrorMessage())
                .status(outboxEvent.getStatus())
                .createdAt(outboxEvent.getCreatedAt())
                .lastTryAt(outboxEvent.getLastTryAt())
                .processedAt(outboxEvent.getProcessedAt())
                .build();
    }

    public OutboxEvent toDomain() {
        return OutboxEvent.builder()
                .id(this.id)
                .eventType(this.eventType)
                .event(deserialize(this.payload, this.eventType))
                .tryCount(this.tryCount)
                .lastErrorMessage(this.lastErrorMessage)
                .status(this.status)
                .createdAt(this.createdAt)
                .lastTryAt(this.lastTryAt)
                .processedAt(this.processedAt)
                .build();
    }
}
