package com.cos.cercat.domain.certificate;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CertificateReader {

    private final CertificateRepository certificateRepository;

    public CertificateReader(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<Certificate> readAll() {
        return certificateRepository.findAll();
    }

    public Certificate read(TargetCertificate targetCertificate) {
        return certificateRepository.findById(targetCertificate);
    }
}
