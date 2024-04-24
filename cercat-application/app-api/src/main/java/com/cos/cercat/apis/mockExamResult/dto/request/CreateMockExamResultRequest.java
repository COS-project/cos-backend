package com.cos.cercat.apis.mockExamResult.dto.request;

import com.cos.cercat.domain.mockexamresult.NewSubjectResult;

import java.util.List;

public record CreateMockExamResultRequest(
        List<CreateSubjectResultRequest> createSubjectResultRequests
) {
    public List<NewSubjectResult> toNewSubjectResults() {
        return createSubjectResultRequests.stream()
                .map(CreateSubjectResultRequest::toNewSubjectResult)
                .toList();
    }
}
