package com.cos.cercat.apis.learning.dto.request;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.learning.domain.Goal;
import com.cos.cercat.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public record GoalCreateRequest(
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

    public Goal toEntity(Certificate certificate, User user) {
        return new Goal(
                certificate,
                user,
                goalScore,
                prepareStartDateTime,
                prepareFinishDateTime,
                goalPrepareDays,
                mockExamsPerDay,
                goalMockExams,
                studyTimePerDay,
                goalStudyTime,
                mockExamRepeatDays,
                studyRepeatDays
        );
    }
}
