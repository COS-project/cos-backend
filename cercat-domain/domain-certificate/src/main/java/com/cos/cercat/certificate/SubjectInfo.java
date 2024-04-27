package com.cos.cercat.certificate;

public record SubjectInfo(
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
}
