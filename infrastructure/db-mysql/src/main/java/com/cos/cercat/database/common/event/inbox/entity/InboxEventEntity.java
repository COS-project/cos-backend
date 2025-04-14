package com.cos.cercat.database.common.event.inbox.entity;

import static com.cos.cercat.database.common.event.EventPayloadConverter.*;

import com.cos.cercat.domain.common.event.EventStatus;
import com.cos.cercat.domain.common.event.inbox.InboxEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(
        name = "inbox_events",
        indexes = {
                @Index(name = "idx_status_created_at", columnList = "status, createdAt")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_event_id", columnNames = {"event_id"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InboxEventEntity {

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

    public static InboxEventEntity from(InboxEvent inboxEvent) {
        return InboxEventEntity.builder()
                .id(inboxEvent.getId())
                .eventType(inboxEvent.getEvent().resolveType())
                .payload(serialize(inboxEvent.getEvent()))
                .tryCount(inboxEvent.getTryCount())
                .lastErrorMessage(inboxEvent.getLastErrorMessage())
                .status(inboxEvent.getStatus())
                .createdAt(inboxEvent.getCreatedAt())
                .lastTryAt(inboxEvent.getLastTryAt())
                .processedAt(inboxEvent.getProcessedAt())
                .build();
    }

    public InboxEvent toDomain() {
        return InboxEvent.builder()
                .id(this.id)
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
