package com.cos.cercat.apis.mockExamResult.dto.request;

import com.cos.cercat.domain.SubjectEntity;
import com.cos.cercat.domain.SubjectResult;
import com.cos.cercat.domain.UserAnswers;

import java.util.List;

public record SubjectResultRequest(
        Long subjectId,
        int score,
        List<UserAnswerRequest> userAnswerRequests
) {
    public SubjectResult toEntity(SubjectEntity subjectEntity, UserAnswers userAnswers) {

        int correctCount = (int) userAnswerRequests.stream()
                .filter(UserAnswerRequest::isCorrect)
                .count();

        return SubjectResult.builder()
                .subjectEntity(subjectEntity)
                .score(score)
                .numberOfCorrect(correctCount)
                .totalTakenTime(userAnswerRequests.stream()
                        .mapToLong(UserAnswerRequest::takenTime)
                        .sum())
                .correctRate((int) ((double) correctCount / subjectEntity.getNumberOfQuestions() * 100))
                .userAnswers(userAnswers)
                .build();
    }

}
