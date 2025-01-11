package com.cos.cercat.apis.mockExamResult.request;


import com.cos.cercat.domain.mockexamresult.NewUserAnswer;

public record CreateUserAnswerRequest(
        Long questionId,
        Integer selectOptionSeq,
        Long takenTime,
        boolean isCorrect
) {

    public NewUserAnswer toNewUserAnswer() {
        return new NewUserAnswer(
                questionId,
                selectOptionSeq,
                takenTime,
                isCorrect
        );
    }
}
