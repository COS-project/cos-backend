package com.cos.cercat.certificate.dto.request;

import com.cos.cercat.certificate.domain.*;

public record ExamInfoCreateRequest(
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
    public ExamInfo toEntity(Certificate certificate) {
        return new ExamInfo(
                CertificateExam.of(certificate, examYear, round),
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
