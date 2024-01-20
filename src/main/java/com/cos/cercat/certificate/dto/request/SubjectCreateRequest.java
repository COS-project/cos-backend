package com.cos.cercat.certificate.dto.request;

import com.cos.cercat.certificate.domain.Subject;

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
