package com.cos.cercat.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CertificateExamReader {

    private final CertificateExamRepository certificateExamRepository;

    public CertificateExam readRecent(Certificate certificate) {
        return certificateExamRepository.findRecentCertificateExam(certificate);
    }


    public boolean isExamDatePassed(CertificateExam certificateExam) {
        if (certificateExam == null) {
            return false;
        }

        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDateTime examDateTime = certificateExam.examInformation().examSchedule().examDateTime();

        return todayDateTime.isAfter(examDateTime);
    }
}
