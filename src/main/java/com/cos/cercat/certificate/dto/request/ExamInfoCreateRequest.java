package com.cos.cercat.certificate.dto.request;

import com.cos.cercat.certificate.domain.*;

public record ExamInfoCreateRequest(
        ExamSchedule examSchedule,
        ExamFee examFee,
        ExamTimeLimit examTimeLimit,
        PassingCriteria passingCriteria,
        String subjectsInfo,
        String description,
        String examFormat,
        String examEligibility
) {
    public ExamInfo toEntity(Certificate certificate) {
        return new ExamInfo(
                certificate,
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
