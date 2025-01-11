package com.cos.cercat.domain.learning;

public record GoalContent(
        Integer goalScore,
        Integer goalPrepareDays,
        Integer mockExamsPerDay,
        Integer goalMockExams,
        Long studyTimePerDay,
        Long goalStudyTime
) {
}
