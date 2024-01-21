package com.cos.cercat.learning.dto.request;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.domain.RepeatDay;
import com.cos.cercat.learning.domain.RepeatType;
import com.cos.cercat.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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
