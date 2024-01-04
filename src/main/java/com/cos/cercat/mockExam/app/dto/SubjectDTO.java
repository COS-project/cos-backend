package com.cos.cercat.mockExam.app.dto;

import com.cos.cercat.mockExam.domain.entity.Subject;

public record SubjectDTO(
        Long subjectId,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
    public static SubjectDTO from(Subject entity) {
        return new SubjectDTO(
                entity.getId(),
                entity.getSubjectName(),
                entity.getNumberOfQuestions(),
                entity.getTotalScore()
        );
    }
}
