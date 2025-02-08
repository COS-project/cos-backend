package com.cos.cercat.domain.mockexam.exception;

import com.cos.cercat.exception.DomainException;
import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class QuestionNotFoundException extends DomainException {

    public static final DomainException EXCEPTION = new QuestionNotFoundException();

    private QuestionNotFoundException() {
        super(MockExamErrorCode.QUESTION_NOT_FOUND);
    }

}
