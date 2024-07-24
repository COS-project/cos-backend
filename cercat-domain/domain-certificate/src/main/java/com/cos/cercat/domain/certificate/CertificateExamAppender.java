package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateExamAppender {

    private final CertificateExamRepository certificateExamRepository;

    public void append(Certificate certificate, NewExamInformation newExamInfo) {
        certificateExamRepository.save(certificate, newExamInfo);
    }

}
