package com.cos.cercat.apis.certificate.dto.response;

import com.cos.cercat.domain.SubjectEntity;

public record SubjectResponse(
        Long subjectId,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {
    public static SubjectResponse from(SubjectEntity entity) {
        return new SubjectResponse(
                entity.getId(),
                entity.getSubjectName(),
                entity.getNumberOfQuestions(),
                entity.getTotalScore()
        );
    }
}
