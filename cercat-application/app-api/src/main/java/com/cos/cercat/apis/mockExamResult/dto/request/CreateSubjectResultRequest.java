package com.cos.cercat.apis.mockExamResult.dto.request;

import com.cos.cercat.mockexamresult.NewSubjectResult;

import java.util.List;

public record CreateSubjectResultRequest(
        Long subjectId,
        int score,
        List<CreateUserAnswerRequest> createUserAnswerRequests
) {
    public NewSubjectResult toNewSubjectResult() {
        return new NewSubjectResult(
                subjectId,
                score,
                (int) createUserAnswerRequests.stream()
                        .filter(CreateUserAnswerRequest::isCorrect)
                        .count(),
                createUserAnswerRequests.stream()
                        .map(CreateUserAnswerRequest::takenTime)
                        .reduce(0L, Long::sum),
                createUserAnswerRequests.stream()
                        .map(CreateUserAnswerRequest::toNewUserAnswer)
                        .toList()
        );
    }
}
