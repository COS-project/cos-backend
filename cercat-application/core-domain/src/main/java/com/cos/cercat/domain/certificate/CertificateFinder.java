package com.cos.cercat.domain.certificate;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CertificateFinder {

    private final CertificateRepository certificateRepository;

    public CertificateFinder(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<Certificate> findAll() {
        return certificateRepository.findAll();
    }


}
