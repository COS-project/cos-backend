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

    private final Long certificateExamId;
    private final Certificate certificate;

}
