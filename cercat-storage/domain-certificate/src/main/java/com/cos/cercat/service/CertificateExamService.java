package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.repository.CertificateExamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CertificateExamService {

    private final CertificateExamJpaRepository certificateExamJpaRepository;

    public void createCertificateExam(CertificateExamEntity certificateExamEntity) {
        certificateExamJpaRepository.save(certificateExamEntity);
    }

    public CertificateExamEntity getRecentCertificateExam(CertificateEntity certificateEntity) {
        return certificateExamJpaRepository.findRecentCertificateExam(certificateEntity.getId());
    }

    public boolean isExamDatePassed(CertificateEntity certificateEntity) {
        CertificateExamEntity recentCertificateExamEntity = getRecentCertificateExam(certificateEntity);

        if (recentCertificateExamEntity == null) {
            return false;
        }

        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDateTime examDateTime = recentCertificateExamEntity.getExamInfoEntity().getExamDateTime();

        return todayDateTime.isAfter(examDateTime);
    }

}
