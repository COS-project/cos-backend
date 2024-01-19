package com.cos.cercat.goal.dto.response;

import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.goal.domain.Goal;
import com.cos.cercat.goal.domain.RepeatDay;

import java.time.LocalDateTime;
import java.util.List;

public record GoalResponse(
        CertificateResponse certificate,
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

    public static GoalResponse from(Goal entity) {
        return new GoalResponse(
                CertificateResponse.from(entity.getCertificate()),
                entity.getGoalScore(),
                entity.getPrepareStartDateTime(),
                entity.getPrepareFinishDateTime(),
                entity.getTotalPrepareDays(),
                entity.getMockExamsPerDay(),
                entity.getTotalMockExams(),
                convertIntegerList(entity.getRepeatDays().getMockExamRepeatDays()),
                entity.getStudyTimePerDay(),
                entity.getTotalStudyTime(),
                convertIntegerList(entity.getRepeatDays().getStudyRepeatDays())
        );
    }

    private static List<Integer> convertIntegerList(List<RepeatDay> repeatDays) {
        return repeatDays.stream()
                .map(repeatDay -> {
                    int dayOfWeekValue = repeatDay.getRepeatDayOfWeek().getValue();
                    return (dayOfWeekValue < RepeatDay.SUNDAY_VALUE) ? dayOfWeekValue : 0;
                }).toList();
    }
}
