package com.cos.cercat.mockExamResult.dto.request;

import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.mockExamResult.domain.SubjectResult;
import com.cos.cercat.mockExamResult.dto.request.UserAnswerRequest;

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
