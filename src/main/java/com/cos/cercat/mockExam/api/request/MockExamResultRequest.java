package com.cos.cercat.mockExam.api.request;


import java.util.List;

public record MockExamResultRequest(
        List<SubjectResultRequest> subjectResultRequests
) {
}
