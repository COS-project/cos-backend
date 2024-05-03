package com.cos.cercat.apis.mockExamResult.request;


import com.cos.cercat.mockexamresult.NewSubjectResult;

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
