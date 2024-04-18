package com.cos.cercat.apis.learning.dto.response;

import com.cos.cercat.apis.certificate.dto.response.CertificateResponse;
import com.cos.cercat.domain.Goal;
import com.cos.cercat.domain.RepeatDay;

import java.time.LocalDateTime;
import java.util.List;

public record GoalDetailResponse(
        Long goalId,
        CertificateResponse certificate,
        Integer goalScore,
        LocalDateTime prepareStartDateTime,
        LocalDateTime prepareFinishDateTime,
        Integer goalPrepareDays,
        Integer mockExamsPerDay,
        Integer goalMockExams,
        List<Integer> mockExamRepeatDays,
        Long studyTimePerDay,
        Long goalStudyTime,
        List<Integer> studyRepeatDays
) {

    public static GoalDetailResponse from(Goal entity) {
        return new GoalDetailResponse(
                entity.getId(),
                CertificateResponse.from(entity.getCertificateEntity()),
                entity.getGoalScore(),
                entity.getPrepareStartDateTime(),
                entity.getPrepareFinishDateTime(),
                entity.getGoalPrepareDays(),
                entity.getMockExamsPerDay(),
                entity.getGoalMockExams(),
                convertIntegerList(entity.getRepeatDays().getMockExamRepeatDays()),
                entity.getStudyTimePerDay(),
                entity.getGoalStudyTime(),
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
