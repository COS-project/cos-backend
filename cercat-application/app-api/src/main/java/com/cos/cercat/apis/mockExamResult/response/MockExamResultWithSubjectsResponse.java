package com.cos.cercat.apis.mockExamResult.response;

import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.mockexamresult.MockExamResultDetail;

import java.util.List;

public record MockExamResultWithSubjectsResponse(
        Long mockExamResultId,
        Integer round,
        MockExamResponse mockExam,
        List<SubjectResultResponse> subjectResults,
        Integer totalScore
) {

    public static MockExamResultWithSubjectsResponse from(MockExamResultDetail mockExamResultDetail) {
        return new MockExamResultWithSubjectsResponse(
                mockExamResultDetail.mockExamResult().getId(),
                mockExamResultDetail.mockExamResult().getRound(),
                MockExamResponse.from(mockExamResultDetail.mockExamResult().getMockExam()),
                mockExamResultDetail.subjectResults().stream()
                        .map(SubjectResultResponse::from)
                        .toList(),
                mockExamResultDetail.mockExamResult().getTotalScore()
        );
    }
}
