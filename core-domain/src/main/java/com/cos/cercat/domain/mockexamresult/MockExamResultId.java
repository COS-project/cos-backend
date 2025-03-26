package com.cos.cercat.domain.mockexamresult;

public record MockExamResultId(
        Long mockExamResultId
) {
    public static MockExamResultId from(Long mockExamResultId) {
        return new MockExamResultId(mockExamResultId);
    }
}
