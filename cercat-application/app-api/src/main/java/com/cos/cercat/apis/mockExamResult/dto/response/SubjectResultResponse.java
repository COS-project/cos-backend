package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.certificate.response.SubjectResponse;
import com.cos.cercat.domain.SubjectResultEntity;

public record SubjectResultResponse(
        SubjectResponse subject,
        Integer score,
        Integer numberOfCorrect,
        Long totalTakenTime,
        Integer correctRate
) {

    public static SubjectResultResponse from(SubjectResultEntity entity) {
        return new SubjectResultResponse(
                SubjectResponse.from(entity.getSubjectEntity()),
                entity.getScore(),
                entity.getNumberOfCorrect(),
                entity.getTotalTakenTime(),
                entity.getCorrectRate()
        );
    }

}
