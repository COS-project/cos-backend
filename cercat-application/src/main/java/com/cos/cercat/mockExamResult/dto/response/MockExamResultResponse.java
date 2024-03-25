package com.cos.cercat.mockExamResult.dto.response;

import com.cos.cercat.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.mockExamResult.domain.MockExamResult;


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
