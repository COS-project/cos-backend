package com.cos.cercat.domain.certificate;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateExamReader {

    private final CertificateExamRepository certificateExamRepository;

    public CertificateExam readPrevious(Certificate certificate) {
        return certificateExamRepository.findPreviousCertificateExam(certificate);
    }

    public CertificateExam readNext(Certificate certificate) {
        return certificateExamRepository.findNextCertificateExam(certificate);
    }

    public List<CertificateExam> read(ExamScheduleType examScheduleType, LocalDateTime dateTime) {
        return switch (examScheduleType) {
            case APPLICATION_START -> certificateExamRepository.findCertificateExamsByApplicationDate(dateTime);
            case DEADLINE -> certificateExamRepository.findCertificateExamsByDeadlineDate(dateTime);
        };
    }

}


