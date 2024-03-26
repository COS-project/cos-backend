package com.cos.cercat.apis.learning.dto.response;

import com.cos.cercat.domain.Goal;

public record GoalAchievementResponse(
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
    public static GoalAchievementResponse of(Goal goal,
                                             Integer currentMaxScore,
                                             Long todayStudyTime,
                                             Long currentStudyTime,
                                             Integer todayMockExams,
                                             Integer currentMockExams) {
        return new GoalAchievementResponse(
                goal.getId(),
                goal.getGoalScore(),
                currentMaxScore,
                goal.getStudyTimePerDay(),
                todayStudyTime,
                goal.getGoalStudyTime(),
                currentStudyTime,
                goal.getMockExamsPerDay(),
                todayMockExams,
                goal.getGoalMockExams(),
                currentMockExams,
                goal.getGoalDDay()
        );
    }
}
