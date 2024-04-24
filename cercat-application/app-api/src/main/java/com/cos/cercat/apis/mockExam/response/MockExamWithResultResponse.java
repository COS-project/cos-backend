package com.cos.cercat.apis.mockExam.response;

import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.domain.MockExamResultEntity;
import com.cos.cercat.apis.mockExamResult.dto.response.MockExamResultWithSubjectsResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record MockExamWithResultResponse(
        Long mockExamId,
        Integer round,
        Boolean isTake,
        MockExamResultWithSubjectsResponse recentMockExamResultWithSubjectsResponse
) {

//    public static MockExamWithResultResponse from(MockExamEntity mockExamEntity, List<MockExamResultEntity> mockExamResultEntities) {
//
//        boolean isTake = !mockExamResultEntities.isEmpty();
//
//        return new MockExamWithResultResponse(
//                mockExamEntity.getId(),
//                mockExamEntity.getRound(),
//                isTake,
//                (isTake) ? MockExamResultWithSubjectsResponse.from(mockExamResultEntities.get(0)) : null
//        );
//    }
}
