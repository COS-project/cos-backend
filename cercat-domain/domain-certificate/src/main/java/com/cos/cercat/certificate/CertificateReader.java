package com.cos.cercat.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificateReader {

    private final CertificateRepository certificateRepository;

    public List<Certificate> readAll() {
        return certificateRepository.findAll();
    }

    public Certificate read(TargetCertificate targetCertificate) {
        return certificateRepository.findById(targetCertificate);
    }
}
