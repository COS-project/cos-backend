package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.certificate.domain.Subject;

public record SubjectCreateRequest(
        String subjectName,
        Integer numberOfQuestion,
        Integer totalScore
) {

    public Subject toEntity() {
        return new Subject(
                subjectName,
                numberOfQuestion,
                totalScore
        );
    }

}
