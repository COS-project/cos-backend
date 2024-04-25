package com.cos.cercat.apis.learning.dto.response;


import com.cos.cercat.domain.GoalEntity;
import com.cos.cercat.domain.learning.Goal;

import java.time.LocalDate;

public record GoalResponse(
        Long goalId,
        LocalDate prepareStartDateTime,
        LocalDate prepareFinishDateTime
) {

    public static GoalResponse from(Goal goal) {
        return new GoalResponse(
                goal.getId(),
                goal.getGoalPeriod().startDateTime().toLocalDate(),
                goal.getGoalPeriod().endDateTime().toLocalDate()
        );
    }
}
