package com.cos.cercat.mockExamResult.dto.response;

import com.cos.cercat.mockExamResult.domain.MockExamResult;

import java.util.List;

public record MockExamResultResponse(
        Long mockExamResultId,
        Integer round,
        List<SubjectResultResponse> subjectResults
) {

    public static MockExamResultResponse from(MockExamResult entity) {
        return new MockExamResultResponse(
                entity.getId(),
                entity.getRound(),
                entity.getSubjectResults().getSubjectResults().stream()
                        .map(SubjectResultResponse::from)
                        .toList()
        );
    }

}
