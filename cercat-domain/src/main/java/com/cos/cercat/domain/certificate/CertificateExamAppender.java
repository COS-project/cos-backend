package com.cos.cercat.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateExamAppender {

    private final CertificateExamRepository certificateExamRepository;

    public void append(TargetCertificate targetCertificate, ExamInformation examInfo) {
        certificateExamRepository.save(targetCertificate, examInfo);
    }

}