package com.cos.cercat.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateExamAppender {

    private final CertificateExamRepository certificateExamRepository;

    public void append(TargetCertificate targetCertificate, NewExamInformation newExamInfo) {
        certificateExamRepository.save(targetCertificate, newExamInfo);
    }

}
