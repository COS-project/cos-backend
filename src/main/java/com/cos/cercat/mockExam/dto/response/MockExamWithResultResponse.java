package com.cos.cercat.mockExam.dto.response;

import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import com.cos.cercat.mockExamResult.dto.response.MockExamResultResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

public record MockExamWithResultResponse(
        Long mockExamId,
        Integer round,
        Boolean isTake,
        @JsonInclude(Include.NON_NULL)
        MockExamResultResponse recentMockExamResultResponse
) {

    public static MockExamWithResultResponse from(MockExam mockExam, List<MockExamResult> mockExamResults) {

        boolean isTake = !mockExamResults.isEmpty();

        return new MockExamWithResultResponse(
                mockExam.getId(),
                mockExam.getRound(),
                isTake,
                (isTake) ? MockExamResultResponse.from(mockExamResults.get(0)) : null
        );
    }
}
