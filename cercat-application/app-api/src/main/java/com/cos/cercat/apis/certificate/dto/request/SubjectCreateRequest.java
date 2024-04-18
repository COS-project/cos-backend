package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.SubjectEntity;

public record SubjectCreateRequest(
        String subjectName,
        Integer numberOfQuestion,
        Integer totalScore
) {

    public SubjectEntity toEntity() {
        return new SubjectEntity(
                subjectName,
                numberOfQuestion,
                totalScore
        );
    }

}
