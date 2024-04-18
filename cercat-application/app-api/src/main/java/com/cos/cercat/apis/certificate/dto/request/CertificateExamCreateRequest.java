package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.*;

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
    public CertificateExam toEntity(CertificateEntity certificateEntity) {
        return CertificateExam.of(
                certificateEntity,
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
