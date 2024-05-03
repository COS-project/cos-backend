package com.cos.cercat.apis.mockExamResult.response;

import com.cos.cercat.apis.certificate.response.SubjectResponse;
import com.cos.cercat.mockexamresult.SubjectResultStatistics;

public record SubjectResultsAVGResponse(
        SubjectResponse subject,
        int correctRate,
        long totalTakenTime
) {


    public static SubjectResultsAVGResponse from(SubjectResultStatistics subjectResultStatistics) {
        return new SubjectResultsAVGResponse(
                SubjectResponse.from(subjectResultStatistics.subject()),
                subjectResultStatistics.correctRate(),
                subjectResultStatistics.totalTakenTime()
        );
    }
}
