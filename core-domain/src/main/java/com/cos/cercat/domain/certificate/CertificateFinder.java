package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificateFinder {

    private final CertificateRepository certificateRepository;

    public List<Certificate> find(List<CertificateId> certificateIds) {
        return certificateRepository.find(certificateIds);
    }

}
