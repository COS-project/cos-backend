package com.cos.cercat.apis.mockExamResult.dto.request;


import java.util.List;

public record MockExamResultRequest(
        List<SubjectResultRequest> subjectResultRequests
) {
}
