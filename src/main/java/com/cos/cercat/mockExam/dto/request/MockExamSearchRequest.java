package com.cos.cercat.mockExam.dto.request;

public record MockExamSearchRequest(
        Long certificateId,
        Integer examYear
) {
}
