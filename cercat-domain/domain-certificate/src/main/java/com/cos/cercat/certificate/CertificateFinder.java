package com.cos.cercat.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificateFinder {

    private final CertificateRepository certificateRepository;

    public List<Certificate> find(List<TargetCertificate> targetCertificates) {
        return certificateRepository.find(targetCertificates);
    }

}
