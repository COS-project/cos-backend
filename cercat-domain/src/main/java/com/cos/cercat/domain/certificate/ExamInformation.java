package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.mockexam.MockExamSession;

public record ExamInformation(
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
