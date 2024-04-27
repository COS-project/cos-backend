package com.cos.cercat.learning;

public record TargetGoal(
        Long goalId

) {
    public static TargetGoal from(Long goalId) {
        return new TargetGoal(
                goalId
        );
    }
}
