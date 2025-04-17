package com.cos.cercat.domain.certificate.event.external;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.ScheduleCheckType;
import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.user.User;

public record InterestCertificateExamScheduleEvent(
        String eventId,
        CertificateExam certificateExam,
        User user,
        ScheduleCheckType scheduleCheckType
) implements Event {

    public static InterestCertificateExamScheduleEvent of(CertificateExam certificateExam, User user, ScheduleCheckType scheduleCheckType) {
        return new InterestCertificateExamScheduleEvent(Event.generateId(), certificateExam, user, scheduleCheckType);
    }

    @Override
    public String resolveId() {
        return eventId;
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
