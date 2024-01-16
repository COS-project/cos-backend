package com.cos.cercat.mockExam.dto.request;

import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.mockExam.domain.SubjectResult;

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
