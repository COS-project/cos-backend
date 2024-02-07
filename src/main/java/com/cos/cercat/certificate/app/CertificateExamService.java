package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.certificate.repository.CertificateExamRepository;
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

    public Boolean isExamDatePassed(Certificate certificate) {
        CertificateExam recentCertificateExam = certificateExamRepository.findRecentCertificateExam(certificate.getId());

        LocalDateTime todayDateTime = LocalDateTime.now().plusHours(9);
        LocalDateTime examDateTime = recentCertificateExam.getExamInfo().getExamSchedule().getExamDateTime();

        return todayDateTime.isAfter(examDateTime);
    }

}
