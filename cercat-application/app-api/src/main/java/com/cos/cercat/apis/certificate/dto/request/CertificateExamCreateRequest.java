package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.*;
import com.cos.cercat.domain.certificate.*;

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
    public ExamInformation toExamInformation() {
        return ExamInformation.of(
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
