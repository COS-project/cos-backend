package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateExamReader {

    private final CertificateExamRepository certificateExamRepository;

    public CertificateExam readRecent(TargetCertificate targetCertificate) {
        return certificateExamRepository.findRecentCertificateExam(targetCertificate);
    }

}
