package com.cos.cercat.database.common.entity;

import com.cos.cercat.domain.certificate.InterestCertificateExamScheduleEvent;
import com.cos.cercat.domain.common.Event;
import com.cos.cercat.domain.common.FailedEvent;
import com.cos.cercat.domain.like.LikeCreatedEvent;
import com.cos.cercat.domain.post.CommentCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "failed_events")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class FailedEventEntity {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "event_key")
    private String eventKey;

    @Column(name = "event_data", columnDefinition = "TEXT", nullable = false)
    private String eventData;

    @Column(name = "retry_count", nullable = false)
    private Integer retryCount;

    @Column(name = "last_error_message", columnDefinition = "TEXT")
    private String lastErrorMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FailedEvent.Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_retry_at")
    private LocalDateTime lastRetryAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;


    /**
     * 도메인 객체로 변환
     */
    public FailedEvent toDomain() {
        try {
            Event event = deserializeEvent(this.eventData, this.eventType);

            return FailedEvent.builder()
                    .id(this.id)
                    .event(event)
                    .eventType(this.eventType)
                    .eventKey(this.eventKey)
                    .retryCount(this.retryCount)
                    .lastErrorMessage(this.lastErrorMessage)
                    .status(status)
                    .createdAt(this.createdAt)
                    .lastRetryAt(this.lastRetryAt)
                    .processedAt(this.processedAt)
                    .build();
        } catch (Exception e) {
            log.error("[FailedEvent:{}] 역직렬화 실패", this.id, e);
            throw new RuntimeException("이벤트 역직렬화 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 도메인 객체로부터 엔티티 생성
     */
    public static FailedEventEntity from(FailedEvent failedEvent) throws JsonProcessingException {
        String eventData = serializeEvent(failedEvent.getEvent());

        return FailedEventEntity.builder()
                .id(failedEvent.getId())
                .eventType(failedEvent.getEventType())
                .eventKey(failedEvent.getEventKey())
                .eventData(eventData)
                .retryCount(failedEvent.getRetryCount())
                .lastErrorMessage(failedEvent.getLastErrorMessage())
                .status(failedEvent.getStatus())
                .createdAt(failedEvent.getCreatedAt())
                .lastRetryAt(failedEvent.getLastRetryAt())
                .processedAt(failedEvent.getProcessedAt())
                .build();
    }

    public void updateFromDomain(FailedEvent failedEvent) {
        this.retryCount = failedEvent.getRetryCount();
        this.lastErrorMessage = failedEvent.getLastErrorMessage();
        this.status = failedEvent.getStatus();
        this.lastRetryAt = failedEvent.getLastRetryAt();
        this.processedAt = failedEvent.getProcessedAt();
    }

    private static String serializeEvent(Event event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event);
    }

    private static Event deserializeEvent(String eventData, String eventType)
            throws JsonProcessingException {
        return switch (eventType) {
            case "interest-certificate-exam-schedule" ->
                    objectMapper.readValue(eventData, InterestCertificateExamScheduleEvent.class);

            case "comment-created" ->
                    objectMapper.readValue(eventData, CommentCreatedEvent.class);

            case "like-created" ->
                    objectMapper.readValue(eventData, LikeCreatedEvent.class);

            default -> {
                log.warn("알 수 없는 이벤트 타입: {}", eventType);
                throw new UnsupportedOperationException("지원하지 않는 이벤트 타입: " + eventType);
            }
        };
    }
}
