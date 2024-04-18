package com.cos.cercat.apis.learning.dto.request;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.Goal;
import com.cos.cercat.domain.UserEntity;

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

    public Goal toEntity(CertificateEntity certificateEntity, UserEntity userEntity) {
        return new Goal(
                certificateEntity,
                userEntity,
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
