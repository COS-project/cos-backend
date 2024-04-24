package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.certificate.response.SubjectResponse;
import com.cos.cercat.dto.SubjectResultsAVG;

public record SubjectResultsAVGResponse(
        SubjectResponse subject,
        Integer correctRate,
        Long totalTakenTime
) {


    public static SubjectResultsAVGResponse from(SubjectResultsAVG dto) {

        return new SubjectResultsAVGResponse(
                SubjectResponse.from(dto.subjectEntity()),
                dto.correctRate().intValue(),
                dto.totalTakenTime().longValue()
        );
    }
}
