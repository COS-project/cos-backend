package com.cos.cercat.mockExam.api.request;

public record MockExamSearchRequest(
        Long certificateId,
        Integer examYear
) {
}
