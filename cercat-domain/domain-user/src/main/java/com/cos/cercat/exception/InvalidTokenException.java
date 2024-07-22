package com.cos.cercat.exception;

import com.cos.cercat.common.exception.DomainException;

public class InvalidTokenException extends DomainException {
    public static final DomainException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(UserErrorCode.INVALID_TOKEN);
    }
}
