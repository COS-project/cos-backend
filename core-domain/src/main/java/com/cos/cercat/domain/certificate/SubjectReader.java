package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectReader {

    private final CertificateRepository certificateRepository;

    public List<Subject> read(Certificate certificate) {
        return certificateRepository.findSubject(certificate);
    }

}
