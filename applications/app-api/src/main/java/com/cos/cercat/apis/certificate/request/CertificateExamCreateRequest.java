package com.cos.cercat.apis.certificate.request;



import com.cos.cercat.domain.certificate.*;

import java.time.LocalDateTime;

public record CertificateExamCreateRequest(
        Integer examYear,
        Integer round,
        LocalDateTime applicationStartDateTime,
        LocalDateTime applicationDeadlineDateTime,
        LocalDateTime resultAnnouncementDateTime,
        LocalDateTime examDateTime,
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
                examYear,
                round,
                ExamSchedule.of(
                        applicationStartDateTime,
                        applicationDeadlineDateTime,
                        resultAnnouncementDateTime,
                        examDateTime
                ),
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
