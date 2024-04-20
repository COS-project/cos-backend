package com.cos.cercat.domain.certificate;

public record Subject(
        long subjectId,
        long CertificateId,
        SubjectInfo subjectInfo
) {
}
