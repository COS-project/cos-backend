package com.cos.cercat.certificate;

public record SubjectInfo(
        Integer subjectSequence,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
}
