package com.cos.cercat.domain.learning;

public record TargetGoal(
        Long goalId

) {
    public static TargetGoal from(Long goalId) {
        return new TargetGoal(
                goalId
        );
    }
}
