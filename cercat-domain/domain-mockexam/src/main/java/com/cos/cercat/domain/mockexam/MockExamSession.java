package com.cos.cercat.domain.mockexam;

public record MockExamSession(
        Integer examYear,
        Integer round
) {

    public static MockExamSession of(int examYear, int round) {
        return new MockExamSession(examYear, round);
    }
}