package com.cos.cercat.mockExamResult.dto.response;

import com.cos.cercat.certificate.dto.response.SubjectResponse;
import com.cos.cercat.mockExamResult.dto.SubjectResultsAVG;

public record SubjectResultsAVGResponse(
        SubjectResponse subject,
        Integer correctRate,
        Long totalTakenTime
) {


    public static SubjectResultsAVGResponse from(SubjectResultsAVG dto) {

        return new SubjectResultsAVGResponse(
                SubjectResponse.from(dto.subject()),
                dto.correctRate().intValue(),
                dto.totalTakenTime().longValue()
        );
    }
}
