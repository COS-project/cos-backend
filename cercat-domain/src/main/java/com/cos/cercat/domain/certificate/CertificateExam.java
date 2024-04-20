package com.cos.cercat.domain.certificate;

public record CertificateExam(
        Certificate certificate,
        ExamInformation examInformation
) {
    public static CertificateExam of(
            Certificate certificate,
            ExamInformation examInformation
    ) {
        return new CertificateExam(
                certificate,
                examInformation
        );
    }
}
