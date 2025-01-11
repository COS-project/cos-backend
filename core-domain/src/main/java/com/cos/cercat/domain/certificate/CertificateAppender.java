package com.cos.cercat.domain.certificate;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CertificateAppender {
    private final CertificateRepository certificateRepository;

    public CertificateAppender(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public void append(String certificateName, List<SubjectInfo> subjectsInfo) {
        certificateRepository.save(certificateName, subjectsInfo);
    }
}
