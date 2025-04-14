package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.ScheduleCheckType;
import com.cos.cercat.domain.user.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class ExamAlarm extends Alarm {

    private final Certificate certificate;

    public static ExamAlarm from(CertificateExam certificateExam, User receiver, ScheduleCheckType scheduleCheckType) {
        return switch (scheduleCheckType) {
            case BEFORE_APPLICATION -> ExamAlarm.builder()
                    .originId(certificateExam.id())
                    .receiver(receiver)
                    .certificate(certificateExam.certificate())
                    .alarmType(AlarmType.BEFORE_APPLICATION)
                    .build();
            case START_APPLICATION -> ExamAlarm.builder()
                    .originId(certificateExam.id())
                    .receiver(receiver)
                    .certificate(certificateExam.certificate())
                    .alarmType(AlarmType.START_APPLICATION)
                    .build();
            case BEFORE_DEADLINE -> ExamAlarm.builder()
                    .originId(certificateExam.id())
                    .receiver(receiver)
                    .certificate(certificateExam.certificate())
                    .alarmType(AlarmType.BEFORE_DEADLINE)
                    .build();
        };
    }

}
