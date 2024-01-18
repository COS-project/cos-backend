package com.cos.cercat.mockExamResult.dto.request;

import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.mockExamResult.domain.SubjectResult;
import com.cos.cercat.mockExamResult.domain.UserAnswer;
import com.cos.cercat.mockExamResult.domain.UserAnswers;
import com.cos.cercat.mockExamResult.dto.request.UserAnswerRequest;

import java.util.List;

public record SubjectResultRequest(
        Long subjectId,
        int score,
        List<UserAnswerRequest> userAnswerRequests
) {
    public SubjectResult toEntity(Subject subject, UserAnswers userAnswers) {

        int correctCount = (int) userAnswerRequests.stream()
                .filter(UserAnswerRequest::is_correct)
                .count();

        return new SubjectResult(
                subject,
                score,
                correctCount,
                userAnswerRequests.stream()
                        .mapToLong(UserAnswerRequest::takenTime)
                        .sum(),
                (int) ((double) correctCount / subject.getNumberOfQuestions() * 100),
                userAnswers
        );
    }

}
