package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.domain.MockExamResult;

import java.time.LocalDateTime;


public record MockExamResultResponse(
        Long mockExamResultId,
        Integer round,
        MockExamResponse mockExam,
        Integer totalScore,
        LocalDateTime createdAt
) {
    public static MockExamResultResponse from(MockExamResult entity) {
        return new MockExamResultResponse(
                entity.getId(),
                entity.getRound(),
                MockExamResponse.from(entity.getMockExam()),
                entity.getTotalScore(),
                entity.getCreatedAt()
        );
    }
}
