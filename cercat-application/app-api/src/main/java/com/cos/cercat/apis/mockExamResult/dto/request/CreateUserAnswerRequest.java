package com.cos.cercat.apis.mockExamResult.dto.request;


import com.cos.cercat.mockexamresult.NewUserAnswer;

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
