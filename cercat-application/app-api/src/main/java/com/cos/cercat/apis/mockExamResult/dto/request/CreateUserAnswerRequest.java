package com.cos.cercat.apis.mockExamResult.dto.request;

import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.UserAnswerEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.mockexamresult.NewUserAnswer;

import java.util.Objects;

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
