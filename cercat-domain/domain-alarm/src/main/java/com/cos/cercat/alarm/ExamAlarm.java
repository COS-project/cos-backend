package com.cos.cercat.alarm;

import com.cos.cercat.certificate.CertificateExam;
import com.cos.cercat.user.User;
import lombok.Getter;

@Getter
public class ExamAlarm extends Alarm {

    private final CertificateExam certificate;

    public ExamAlarm(User receiver, AlarmType alarmType, CertificateExam certificate) {
        super(receiver, alarmType);
        this.certificate = certificate;
    }
}
