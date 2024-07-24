package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectFinder {

    private final CertificateRepository certificateRepository;

    public List<Subject> find(Certificate certificate) {
        return certificateRepository.findSubject(certificate);
    }

}
