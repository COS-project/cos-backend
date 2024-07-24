package com.cos.cercat.domain.mockexam;

public record TargetMockExam(
        Long mockExamId
) {
    public static TargetMockExam from(Long mockExamId) {
        return new TargetMockExam(mockExamId);
    }
}
