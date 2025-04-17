package com.cos.cercat.domain.learning;

import java.util.List;
import java.util.stream.Stream;

public record NewGoal(
        GoalPeriod goalPeriod,
        GoalContent goalContent,
        List<StudySchedule> studySchedules
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
    
    static List<StudySchedule> convertStudySchedule(List<Integer> mockExamSchedules, List<Integer> studySchedules) {
        Stream<StudySchedule> mockExamDaysStream = mockExamSchedules.stream()
                .map(dayOfWeekValue -> StudySchedule.from(ScheduleType.MOCK_EXAM, dayOfWeekValue));

        Stream<StudySchedule> studyExamDaysStream = studySchedules.stream()
                .map(dayOfWeekValue -> StudySchedule.from(ScheduleType.STUDY, dayOfWeekValue));

        return Stream.concat(mockExamDaysStream, studyExamDaysStream).toList();
    }
}
