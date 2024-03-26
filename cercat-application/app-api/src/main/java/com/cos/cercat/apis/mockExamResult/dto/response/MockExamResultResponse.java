package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.domain.MockExamResult;


public record MockExamResultResponse(
        Long mockExamResultId,
        Integer round,
        MockExamResponse mockExam,
        Integer totalScore
) {
    public static MockExamResultResponse from(MockExamResult entity) {
        return new MockExamResultResponse(
                entity.getId(),
                entity.getRound(),
                MockExamResponse.from(entity.getMockExam()),
                entity.getTotalScore()
        );
    }
}
