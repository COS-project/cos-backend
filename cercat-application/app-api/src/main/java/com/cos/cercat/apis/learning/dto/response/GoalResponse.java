package com.cos.cercat.apis.learning.dto.response;


import com.cos.cercat.domain.Goal;

import java.time.LocalDate;

public record GoalResponse(
        Long goalId,
        LocalDate prepareStartDateTime,
        LocalDate prepareFinishDateTime
) {

    public static GoalResponse from(Goal entity) {
        return new GoalResponse(
                entity.getId(),
                entity.getPrepareStartDateTime().toLocalDate(),
                entity.getPrepareFinishDateTime().toLocalDate()
        );
    }
}
