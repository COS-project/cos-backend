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
    public CertificateExam toEntity(Certificate certificate) {
        return CertificateExam.of(
                certificate,
                ExamInfo.of(
                        examSchedule,
                        examFee,
                        examTimeLimit,
                        passingCriteria,
                        subjectsInfo,
                        description,
                        examFormat,
                        examEligibility
                ),
                examYear,
                round
        );
    }
}
