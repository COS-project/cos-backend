package com.cos.cercat.domain.mockexam;

public record MockExamId(
        Long mockExamId
) {
    public static MockExamId from(Long mockExamId) {
        return new MockExamId(mockExamId);
    }
}
