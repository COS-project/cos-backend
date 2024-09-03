package com.cos.cercat.infra.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class UserAnswerNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new UserAnswerNotFoundException();

    private UserAnswerNotFoundException() {
        super(GlobalErrorCode.USER_ANSWER_NOT_FOUND);
    }
}
