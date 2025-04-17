package com.cos.cercat.domain.learning;

public record GoalAchievement(
        Long goalId,
        Integer goalScore,
        Integer maxScore,
        Long studyTimePerDay,
        Long todayStudyTime,
        Long goalStudyTime,
        Long currentStudyTime,
        Integer mockExamsPerDay,
        Integer todayMockExams,
        Integer goalMockExams,
        Integer currentMockExams,
        Integer goalDDay
) {

    public static GoalAchievement of(Goal goal,
                                     Integer currentMaxScore,
                                     Integer todayMockExams,
                                     Integer currentMockExams,
                                     Long todayStudyTime,
                                     Long currentStudyTime) {

        return new GoalAchievement(
                goal.getId(),
                goal.getGoalContent().goalScore(),
                currentMaxScore,
                goal.getGoalContent().studyTimePerDay(),
                todayStudyTime,
                goal.getGoalContent().goalStudyTime(),
                currentStudyTime,
                goal.getGoalContent().mockExamsPerDay(),
                todayMockExams,
                goal.getGoalContent().goalMockExams(),
                currentMockExams,
                goal.getGoalDDay()
        );
    }
}
