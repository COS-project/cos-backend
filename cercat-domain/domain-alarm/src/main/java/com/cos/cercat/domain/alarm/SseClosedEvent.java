package com.cos.cercat.domain.alarm;

public record SseClosedEvent(
        Long userId
) {
    public static SseClosedEvent from(Long userId) {
        return new SseClosedEvent(userId);
    }
}
