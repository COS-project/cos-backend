package com.cos.cercat.goal.dto.request;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.goal.domain.Goal;
import com.cos.cercat.goal.domain.RepeatDay;
import com.cos.cercat.goal.domain.RepeatType;
import com.cos.cercat.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public record GoalCreateRequest(
        Integer goalScore,
        LocalDateTime prepareStartDateTime,
        LocalDateTime prepareFinishDateTime,
        Integer totalPrepareDays,
        Integer mockExamsPerDay,
        Integer totalMockExams,
        List<Integer> mockExamRepeatDays,
        Long studyTimePerDay,
        Long totalStudyTime,
        List<Integer> studyRepeatDays
) {

    public Goal toEntity(Certificate certificate, User user) {
        return new Goal(
                certificate,
                user,
                goalScore,
                prepareStartDateTime,
                prepareFinishDateTime,
                totalPrepareDays,
                mockExamsPerDay,
                totalMockExams,
                studyTimePerDay,
                totalStudyTime,
                convertRepeatDay(mockExamRepeatDays, studyRepeatDays)
        );
    }

    private List<RepeatDay> convertRepeatDay(List<Integer> mockExamRepeatDays, List<Integer> studyRepeatDays) {
        Stream<RepeatDay> mockExamDaysStream = mockExamRepeatDays.stream()
                .map(dayOfWeekValue -> RepeatDay.from(RepeatType.MOCK_EXAM, dayOfWeekValue));

        Stream<RepeatDay> studyExamDaysStream = studyRepeatDays.stream()
                .map(dayOfWeekValue -> RepeatDay.from(RepeatType.STUDY, dayOfWeekValue));

        return Stream.concat(mockExamDaysStream, studyExamDaysStream).toList();
    }
}
