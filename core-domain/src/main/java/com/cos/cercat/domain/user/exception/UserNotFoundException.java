package com.cos.cercat.domain.user.exception;

import com.cos.cercat.exception.DomainException;

public class UserNotFoundException extends DomainException {

    public static final DomainException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(UserErrorCode.NOT_FOUND_USER);
    }
}
