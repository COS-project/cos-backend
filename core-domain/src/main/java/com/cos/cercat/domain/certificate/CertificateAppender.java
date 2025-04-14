package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificateAppender {
    private final CertificateRepository certificateRepository;

    public void append(String certificateName, List<SubjectInfo> subjectsInfo) {
        certificateRepository.save(certificateName, subjectsInfo);
    }
}
