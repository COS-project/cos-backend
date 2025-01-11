package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ExamAlarm extends Alarm {

    private final CertificateExam certificate;

    public ExamAlarm(Long alarmId, User receiver, AlarmType alarmType, CertificateExam certificate,
            LocalDateTime alarmTime) {
        super(alarmId, receiver, alarmType, alarmTime);
        this.certificate = certificate;
    }
}
