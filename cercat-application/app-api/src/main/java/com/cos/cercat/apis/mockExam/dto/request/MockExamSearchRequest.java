package com.cos.cercat.apis.mockExam.dto.request;

public record MockExamSearchRequest(
        Long certificateId,
        Integer examYear
) {
}
