package com.cos.cercat.domain.certificate;

public record CertificateExam(
        Long id,
        Certificate certificate,
        ExamInformation examInformation
) {
}
