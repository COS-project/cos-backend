package com.cos.cercat.apis.learning.response;

import com.cos.cercat.apis.certificate.response.CertificateResponse;
import com.cos.cercat.domain.learning.Goal;
import com.cos.cercat.domain.learning.GoalContent;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.learning.GoalSchedule;

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

    public static GoalDetailResponse from(Goal goal) {
        GoalContent goalContent = goal.getGoalContent();
        GoalPeriod goalPeriod = goal.getGoalPeriod();
        return new GoalDetailResponse(
                goal.getId(),
                CertificateResponse.from(goal.getCertificate()),
                goalContent.goalScore(),
                goalPeriod.startDateTime(),
                goalPeriod.endDateTime(),
                goalContent.goalPrepareDays(),
                goalContent.mockExamsPerDay(),
                goalContent.goalMockExams(),
                convertIntegerList(goal.getMockExamSchedules()),
                goalContent.studyTimePerDay(),
                goalContent.goalStudyTime(),
                convertIntegerList(goal.getStudySchedules())
        );
    }

    private static List<Integer> convertIntegerList(List<GoalSchedule> goalSchedules) {
        return goalSchedules.stream()
                .map(studySchedule -> {
                    int dayOfWeekValue = studySchedule.getRepeatDayOfWeek().getValue();
                    return (dayOfWeekValue < GoalSchedule.SUNDAY_VALUE) ? dayOfWeekValue : 0;
                }).toList();
    }
}
