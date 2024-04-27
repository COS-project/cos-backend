package com.cos.cercat.mockexamresult;

public record TargetMockExamResult(
        Long mockExamResultId
) {
    public static TargetMockExamResult from(Long mockExamResultId) {
        return new TargetMockExamResult(mockExamResultId);
    }
}
