package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.common.Event;
import com.cos.cercat.domain.user.User;

public record InterestCertificateExamScheduleEvent(
        CertificateExam certificateExam,
        User user,
        ScheduleCheckType scheduleCheckType
) implements Event {

    public static InterestCertificateExamScheduleEvent of(CertificateExam certificateExam, User user, ScheduleCheckType scheduleCheckType) {
        return new InterestCertificateExamScheduleEvent(certificateExam, user, scheduleCheckType);
    }

    @Override
    public String resolveKey() {
        return certificateExam.id().toString();
    }

    @Override
    public String resolveType() {
        return "interest-certificate-exam-schedule";
    }

}
