package com.cos.cercat.certificate;


public record NewExamInformation(
        IMockExamSession mockExamSession,
        ExamSchedule examSchedule,
        ExamTimeLimit examTimeLimit,
        ExamFee examFee,
        PassingCriteria passingCriteria,
        String subjectsInfo,
        String description,
        String examFormat,
        String examEligibility
) {

    public static NewExamInformation of(
            IMockExamSession mockExamSession,
            ExamSchedule examSchedule,
            ExamTimeLimit examTimeLimit,
            ExamFee examFee,
            PassingCriteria passingCriteria,
            String subjectsInfo,
            String description,
            String examFormat,
            String examEligibility
    ) {
        return new NewExamInformation(
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
