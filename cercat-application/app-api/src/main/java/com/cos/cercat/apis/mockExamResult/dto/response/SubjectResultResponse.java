package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.certificate.response.SubjectResponse;
import com.cos.cercat.mockexamresult.SubjectResult;

public record SubjectResultResponse(
        SubjectResponse subject,
        Integer score,
        Integer numberOfCorrect,
        Long totalTakenTime,
        Integer correctRate
) {

    public static SubjectResultResponse from(SubjectResult subjectResult) {
        return new SubjectResultResponse(
                SubjectResponse.from(subjectResult.subject()),
                subjectResult.score(),
                subjectResult.numberOfCorrect(),
                subjectResult.totalTakenTime(),
                subjectResult.correctRate(
                ));
    }

}
