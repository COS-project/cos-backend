package com.cos.cercat.mockExam.dto.request;


import java.util.List;

public record MockExamResultRequest(
        List<SubjectResultRequest> subjectResultRequests
) {
}
