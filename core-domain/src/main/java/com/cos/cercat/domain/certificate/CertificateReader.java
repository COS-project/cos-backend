package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificateReader {

    private final CertificateRepository certificateRepository;
    private final CertificateCache certificateCache;

    public List<Certificate> readAll() {
        return certificateRepository.findAll();
    }

    public Certificate read(CertificateId certificateId) {
        return certificateCache.get(certificateId)
                .orElseGet(() -> {
                    Certificate certificate = certificateRepository.findById(certificateId);
                    certificateCache.cache(certificate);
                    return certificate;
                });
    }
}
