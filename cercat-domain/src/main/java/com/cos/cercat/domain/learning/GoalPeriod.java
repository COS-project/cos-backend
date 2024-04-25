package com.cos.cercat.domain.learning;

import java.time.LocalDateTime;

public record GoalPeriod(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
