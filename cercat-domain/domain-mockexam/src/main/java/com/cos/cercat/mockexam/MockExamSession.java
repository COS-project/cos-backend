package com.cos.cercat.mockexam;

public record MockExamSession(
        int examYear,
        int round
) {

    public static MockExamSession of(int examYear, int round) {
        return new MockExamSession(examYear, round);
    }
}