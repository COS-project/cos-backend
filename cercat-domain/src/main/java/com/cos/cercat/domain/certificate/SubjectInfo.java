package com.cos.cercat.domain.certificate;

public record SubjectInfo(
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
}
