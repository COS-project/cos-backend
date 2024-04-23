package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.user.User;
import lombok.Getter;

@Getter
public class ExamAlarm extends Alarm {

    private final CertificateExam certificate;

    public ExamAlarm(User receiver, AlarmType alarmType, CertificateExam certificate) {
        super(receiver, alarmType);
        this.certificate = certificate;
    }
}
