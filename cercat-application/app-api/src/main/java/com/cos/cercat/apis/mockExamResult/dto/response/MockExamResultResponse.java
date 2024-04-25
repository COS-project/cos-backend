package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.domain.MockExamResultEntity;
import com.cos.cercat.domain.mockexamresult.MockExamResult;

import java.time.LocalDateTime;


public record MockExamResultResponse(
        Long mockExamResultId,
        Integer round,
        MockExamResponse mockExam,
        Integer totalScore,
        LocalDateTime createdAt
) {

    public static MockExamResultResponse from(MockExamResult mockExamResult) {
        return new MockExamResultResponse(
                mockExamResult.getId(),
                mockExamResult.getRound(),
                MockExamResponse.from(mockExamResult.getMockExam()),
                mockExamResult.getTotalScore(),
                mockExamResult.getCreatedAt()
        );
    }
}
