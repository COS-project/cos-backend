package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ExamAlarm extends Alarm {

    private final Certificate certificate;

    public static ExamAlarm from(CertificateExam certificateExam, User receiver, AlarmType alarmType) {
        return ExamAlarm.builder()
                .receiver(receiver)
                .originId(certificateExam.id())
                .alarmType(alarmType)
                .certificate(certificateExam.certificate())
                .build();
    }

}
