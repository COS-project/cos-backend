package com.cos.cercat.certificate;

public record CertificateExam(
        Long id,
        Certificate certificate,
        ExamInformation examInformation
) {
}
