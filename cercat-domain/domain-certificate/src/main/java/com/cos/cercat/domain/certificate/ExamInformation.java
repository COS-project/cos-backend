package com.cos.cercat.domain.certificate;


public record ExamInformation(
        Long id,
        int examYear,
        int round,
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
            long id,
            int examYear,
            int round,
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
