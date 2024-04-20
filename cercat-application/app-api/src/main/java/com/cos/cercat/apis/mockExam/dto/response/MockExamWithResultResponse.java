package com.cos.cercat.apis.mockExam.dto.response;

import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.domain.MockExamResult;
import com.cos.cercat.apis.mockExamResult.dto.response.MockExamResultWithSubjectsResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

public record MockExamWithResultResponse(
        Long mockExamId,
        Integer round,
        Boolean isTake,
        @JsonInclude(Include.NON_NULL)
        MockExamResultWithSubjectsResponse recentMockExamResultWithSubjectsResponse
) {

    public static MockExamWithResultResponse from(MockExamEntity mockExamEntity, List<MockExamResult> mockExamResults) {

        boolean isTake = !mockExamResults.isEmpty();

        return new MockExamWithResultResponse(
                mockExamEntity.getId(),
                mockExamEntity.getRound(),
                isTake,
                (isTake) ? MockExamResultWithSubjectsResponse.from(mockExamResults.get(0)) : null
        );
    }
}
