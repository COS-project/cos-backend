package com.cos.cercat.database.mockexamresult.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class UserAnswerNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new UserAnswerNotFoundException();

    private UserAnswerNotFoundException() {
        super(GlobalErrorCode.USER_ANSWER_NOT_FOUND);
    }
}
