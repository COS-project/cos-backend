package com.cos.cercat.database.common.event;

import com.cos.cercat.domain.certificate.event.external.InterestCertificateExamScheduleEvent;
import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.like.event.external.LikeCreatedEvent;
import com.cos.cercat.domain.post.event.external.CommentCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EventPayloadConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String serialize(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("이벤트 직렬화 실패", e);
        }
    }

    public static Event deserialize(String payload, String eventType) {
        try {
            return switch (eventType) {
                case "like-created" -> objectMapper.readValue(payload, LikeCreatedEvent.class);
                case "comment-created" -> objectMapper.readValue(payload, CommentCreatedEvent.class);
                case "interest-certificate-exam-schedule" ->
                        objectMapper.readValue(payload, InterestCertificateExamScheduleEvent.class);
                default -> throw new UnsupportedOperationException("지원하지 않는 이벤트 타입: " + eventType);
            };
        } catch (JsonProcessingException e) {
            throw new RuntimeException("이벤트 역직렬화 실패", e);
        }
    }
}
