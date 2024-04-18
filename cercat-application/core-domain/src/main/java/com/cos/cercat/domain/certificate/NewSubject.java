package com.cos.cercat.domain.certificate;

public record NewSubject(
        long CertificateId,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
}
