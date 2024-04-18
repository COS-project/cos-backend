package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.certificate.dto.response.SubjectResponse;
import com.cos.cercat.domain.SubjectResult;

public record SubjectResultResponse(
        SubjectResponse subject,
        Integer score,
        Integer numberOfCorrect,
        Long totalTakenTime,
        Integer correctRate
) {

    public static SubjectResultResponse from(SubjectResult entity) {
        return new SubjectResultResponse(
                SubjectResponse.from(entity.getSubjectEntity()),
                entity.getScore(),
                entity.getNumberOfCorrect(),
                entity.getTotalTakenTime(),
                entity.getCorrectRate()
        );
    }

}
