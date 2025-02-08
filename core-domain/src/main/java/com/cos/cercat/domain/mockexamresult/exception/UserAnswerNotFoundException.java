package com.cos.cercat.domain.mockexamresult.exception;

import com.cos.cercat.exception.DomainException;
import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class UserAnswerNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new UserAnswerNotFoundException();

    private UserAnswerNotFoundException() {
        super(MockExamResultErrorCode.USER_ANSWER_NOT_FOUND);
    }
}
