package com.cos.cercat.service;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.repository.CertificateExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CertificateExamService {

    private final CertificateExamRepository certificateExamRepository;

    public void createCertificateExam(CertificateExam certificateExam) {
        certificateExamRepository.save(certificateExam);
    }

    public CertificateExam getRecentCertificateExam(Certificate certificate) {
        return certificateExamRepository.findRecentCertificateExam(certificate.getId());
    }

    public boolean isExamDatePassed(Certificate certificate) {
        CertificateExam recentCertificateExam = getRecentCertificateExam(certificate);

        if (recentCertificateExam == null) {
            return false;
        }

        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDateTime examDateTime = recentCertificateExam.getExamInfo().getExamSchedule().getExamDateTime();

        return todayDateTime.isAfter(examDateTime);
    }

}
