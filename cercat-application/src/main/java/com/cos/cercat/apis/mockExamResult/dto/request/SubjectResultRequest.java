package com.cos.cercat.apis.mockExamResult.dto.request;

import com.cos.cercat.domain.certificate.domain.Subject;
import com.cos.cercat.domain.mockExamResult.domain.SubjectResult;
import com.cos.cercat.domain.mockExamResult.domain.UserAnswers;

import java.util.List;

public record SubjectResultRequest(
        Long subjectId,
        int score,
        List<UserAnswerRequest> userAnswerRequests
) {
    public SubjectResult toEntity(Subject subject, UserAnswers userAnswers) {

        int correctCount = (int) userAnswerRequests.stream()
                .filter(UserAnswerRequest::isCorrect)
                .count();

        return SubjectResult.builder()
                .subject(subject)
                .score(score)
                .numberOfCorrect(correctCount)
                .totalTakenTime(userAnswerRequests.stream()
                        .mapToLong(UserAnswerRequest::takenTime)
                        .sum())
                .correctRate((int) ((double) correctCount / subject.getNumberOfQuestions() * 100))
                .userAnswers(userAnswers)
                .build();
    }

}
