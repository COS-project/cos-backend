package com.cos.cercat.apis.certificate.request;

import com.cos.cercat.domain.certificate.*;
import com.cos.cercat.domain.mockexam.MockExamSession;

public record CertificateExamCreateRequest(
        MockExamSession mockExamSession,
        ExamSchedule examSchedule,
        ExamFee examFee,
        ExamTimeLimit examTimeLimit,
        PassingCriteria passingCriteria,
        String subjectsInfo,
        String description,
        String examFormat,
        String examEligibility
) {
    public NewExamInformation toNewExamInformation() {
        return NewExamInformation.of(
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
