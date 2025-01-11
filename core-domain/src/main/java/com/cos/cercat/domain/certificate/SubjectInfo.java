package com.cos.cercat.domain.certificate;

public record SubjectInfo(
        Integer subjectSequence,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
}
