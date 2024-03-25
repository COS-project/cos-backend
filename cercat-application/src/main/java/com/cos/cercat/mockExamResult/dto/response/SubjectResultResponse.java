package com.cos.cercat.mockExamResult.dto.response;

import com.cos.cercat.certificate.dto.response.SubjectResponse;
import com.cos.cercat.mockExamResult.domain.SubjectResult;

public record SubjectResultResponse(
        SubjectResponse subject,
        Integer score,
        Integer numberOfCorrect,
        Long totalTakenTime,
        Integer correctRate
) {

    public static SubjectResultResponse from(SubjectResult entity) {
        return new SubjectResultResponse(
                SubjectResponse.from(entity.getSubject()),
                entity.getScore(),
                entity.getNumberOfCorrect(),
                entity.getTotalTakenTime(),
                entity.getCorrectRate()
        );
    }

}
