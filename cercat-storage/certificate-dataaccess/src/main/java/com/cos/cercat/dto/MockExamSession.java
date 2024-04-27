package com.cos.cercat.dto;

import lombok.Getter;

public record MockExamSession(
        int examYear,
        int round
) {

    public static MockExamSession of(int examYear, int round) {
        return new MockExamSession(examYear, round);
    }
}
