package com.cos.cercat.apis.mockExam.request;

public record MockExamSearchRequest(
        Long certificateId,
        Integer examYear
) {
}
