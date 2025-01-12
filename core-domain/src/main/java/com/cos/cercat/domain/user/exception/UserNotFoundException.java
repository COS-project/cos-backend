package com.cos.cercat.domain.user.exception;

import com.cos.cercat.common.exception.DomainException;
import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class UserNotFoundException extends DomainException {

    public static final DomainException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(UserErrorCode.NOT_FOUND_USER);
    }
}
