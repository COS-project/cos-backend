package com.cos.cercat.security.exception;

import com.cos.cercat.exception.DomainException;

public class InvalidTokenException extends DomainException {
    public static final DomainException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(SecurityErrorCode.INVALID_TOKEN);
    }
}
