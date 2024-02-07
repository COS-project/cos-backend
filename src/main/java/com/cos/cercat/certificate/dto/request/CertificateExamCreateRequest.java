package com.cos.cercat.certificate.dto.request;

import com.cos.cercat.certificate.domain.*;

public record CertificateExamCreateRequest(
        Integer examYear,
        Integer round,
        ExamSchedule examSchedule,
        ExamFee examFee,
        ExamTimeLimit examTimeLimit,
        PassingCriteria passingCriteria,
        String subjectsInfo,
        String description,
        String examFormat,
        String examEligibility
) {
    public ExamInfo toEntity() {
        return new ExamInfo(
                examSchedule,
                examFee,
                examTimeLimit,
                passingCriteria,
                subjectsInfo,
                description,
                examFormat,
                examEligibility
        );
    }
}
