package com.cos.cercat.domain.mockexam;

public record MockExamInfo(
        MockExamSession session,
        Long timeLimit,
        int maxScore
) {
}
