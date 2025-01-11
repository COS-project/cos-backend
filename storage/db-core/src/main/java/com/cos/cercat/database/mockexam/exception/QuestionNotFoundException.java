package com.cos.cercat.database.mockexam.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class QuestionNotFoundException extends InfraException {

    public static final QuestionNotFoundException EXCEPTION = new QuestionNotFoundException();

    private QuestionNotFoundException() {
        super(GlobalErrorCode.QUESTION_NOT_FOUND);
    }

}
