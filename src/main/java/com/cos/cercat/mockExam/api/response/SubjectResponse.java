package com.cos.cercat.mockExam.api.response;

import com.cos.cercat.mockExam.domain.entity.Subject;

public record SubjectResponse(
        Long subjectId,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
    public static SubjectResponse from(Subject entity) {
        return new SubjectResponse(
                entity.getId(),
                entity.getSubjectName(),
                entity.getNumberOfQuestions(),
                entity.getTotalScore()
        );
    }
}
