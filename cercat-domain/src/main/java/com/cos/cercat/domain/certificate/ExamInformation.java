package com.cos.cercat.domain.certificate;

public record ExamInformation(
        Integer examYear,
        Integer round,
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
            Integer examYear,
            Integer round,
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
                examYear,
                round,
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
