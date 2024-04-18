package com.cos.cercat.domain.certificate;

public record Subject(
        long subjectId,
        long CertificateId,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
}
