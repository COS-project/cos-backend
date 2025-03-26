package com.cos.cercat.domain.learning;

public record GoalId(
        Long goalId

) {
    public static GoalId from(Long goalId) {
        return new GoalId(
                goalId
        );
    }
}
