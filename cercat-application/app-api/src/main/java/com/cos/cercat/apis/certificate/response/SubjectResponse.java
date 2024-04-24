package com.cos.cercat.apis.certificate.response;

import com.cos.cercat.domain.SubjectEntity;
import com.cos.cercat.domain.certificate.Subject;

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

    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
                subject.subjectId(),
                subject.subjectInfo().subjectName(),
                subject.subjectInfo().numberOfQuestions(),
                subject.subjectInfo().totalScore()
        );
    }
}
