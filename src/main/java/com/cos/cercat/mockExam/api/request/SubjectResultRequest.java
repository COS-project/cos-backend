package com.cos.cercat.mockExam.api.request;

import com.cos.cercat.mockExam.domain.entity.Subject;
import com.cos.cercat.mockExam.domain.entity.SubjectResult;

import java.util.List;

public record SubjectResultRequest(
        Long subjectId,
        int score,
        List<UserAnswerRequest> userAnswerRequests
) {
    public SubjectResult toEntity(Subject subject) {
        return new SubjectResult(
                subject,
                score
        );
    }
}
