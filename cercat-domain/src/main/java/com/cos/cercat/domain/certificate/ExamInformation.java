package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.mockexam.MockExamSession;

public record ExamInformation(
        Long id,
        MockExamSession mockExamSession,
        ExamSchedule examSchedule,
        ExamTimeLimit examTimeLimit,
        ExamFee examFee,
        PassingCriteria passingCriteria,
        String subjectsInfo,
        String description,
        String examFormat,
        String examEligibility
) {
    public static ExamInformation of(
            Long id,
            MockExamSession mockExamSession,
            ExamSchedule examSchedule,
            ExamTimeLimit examTimeLimit,
            ExamFee examFee,
            PassingCriteria passingCriteria,
            String subjectsInfo,
            String description,
            String examFormat,
            String examEligibility
    ) {
        return new ExamInformation(
                id,
                mockExamSession,
                examSchedule,
                examTimeLimit,
                examFee,
                passingCriteria,
                subjectsInfo,
                description,
                examFormat,
                examEligibility
        );
    }
}
