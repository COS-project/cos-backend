package com.cos.cercat.mockexam;

public record TargetMockExam(
        Long mockExamId
) {
    public static TargetMockExam from(Long mockExamId) {
        return new TargetMockExam(mockExamId);
    }
}
