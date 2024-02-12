package com.cos.cercat.mockExamResult.dto.response;

public record Report<T>(
        double average,
        T scoreAVGList
) {

    public static <T> Report<T> of(double average, T scoreAVGList) {
        return new Report<>(
                average,
                scoreAVGList
        );
    }

}
