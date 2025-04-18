package com.cos.cercat.domain.learning;

import java.util.List;
import java.util.stream.Stream;

public record NewGoal(
        GoalPeriod goalPeriod,
        GoalContent goalContent,
        List<GoalSchedule> goalSchedules
) {

    public static NewGoal of(GoalPeriod goalPeriod,
                             GoalContent goalContent,
                             List<Integer> mockExamSchedules,
                             List<Integer> studySchedules) {
        return new NewGoal(
                goalPeriod,
                goalContent,
                convertStudySchedule(mockExamSchedules, studySchedules)
        );
    }

    static List<GoalSchedule> convertStudySchedule(List<Integer> mockExamSchedules, List<Integer> studySchedules) {
        Stream<GoalSchedule> mockExamDaysStream = mockExamSchedules.stream()
                .map(dayOfWeekValue -> GoalSchedule.from(ScheduleType.MOCK_EXAM, dayOfWeekValue));

        Stream<GoalSchedule> studyExamDaysStream = studySchedules.stream()
                .map(dayOfWeekValue -> GoalSchedule.from(ScheduleType.STUDY, dayOfWeekValue));

        return Stream.concat(mockExamDaysStream, studyExamDaysStream).toList();
    }
}
