package com.cos.cercat.apis.learning.response;



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
