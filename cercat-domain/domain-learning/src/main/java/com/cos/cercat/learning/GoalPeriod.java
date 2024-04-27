package com.cos.cercat.learning;

import java.time.LocalDateTime;
import java.time.Period;

public record GoalPeriod(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
    public Period getTotalPeriod() {
        return Period.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());
    }
}
