package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.domain.MockExamResultEntity;

import java.time.LocalDateTime;


public record MockExamResultResponse(
        Long mockExamResultId,
        Integer round,
        MockExamResponse mockExam,
        Integer totalScore,
        LocalDateTime createdAt
) {
    public static MockExamResultResponse from(MockExamResultEntity entity) {
        return new MockExamResultResponse(
                entity.getId(),
                entity.getRound(),
                MockExamResponse.from(entity.getMockExamEntity()),
                entity.getTotalScore(),
                entity.getCreatedAt()
        );
    }
}
