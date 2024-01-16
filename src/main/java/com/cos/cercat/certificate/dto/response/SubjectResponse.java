package com.cos.cercat.certificate.dto.response;

import com.cos.cercat.certificate.domain.Subject;

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
