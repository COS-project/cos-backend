package com.cos.cercat.mockExamResult.dto.request;


import com.cos.cercat.mockExamResult.dto.request.SubjectResultRequest;

import java.util.List;

public record MockExamResultRequest(
        List<SubjectResultRequest> subjectResultRequests
) {
}
