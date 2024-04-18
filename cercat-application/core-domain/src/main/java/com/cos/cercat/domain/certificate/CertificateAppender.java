package com.cos.cercat.domain.certificate;

import org.springframework.stereotype.Component;

@Component
public class CertificateAppender {
    private final CertificateRepository certificateRepository;

    public CertificateAppender(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public void append(NewCertificate newCertificate) {
        certificateRepository.append(newCertificate);
    }
}
