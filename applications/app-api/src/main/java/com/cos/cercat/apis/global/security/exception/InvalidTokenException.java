package com.cos.cercat.apis.global.security.exception;

import com.cos.cercat.common.exception.DomainException;

public class InvalidTokenException extends DomainException {
    public static final DomainException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(SecurityErrorCode.INVALID_TOKEN);
    }
}
