package com.cos.cercat.apis.certificate.response;


import com.cos.cercat.certificate.Subject;

public record SubjectResponse(
        Long subjectId,
        String subjectName,
        int numberOfQuestions,
        int totalScore
) {

    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
                subject.subjectId(),
                subject.subjectInfo().subjectName(),
                subject.subjectInfo().numberOfQuestions(),
                subject.subjectInfo().totalScore()
        );
    }
}
