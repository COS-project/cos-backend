package com.cos.cercat.security.exception;

import com.cos.cercat.exception.DomainException;

public class TokenParseFailedException extends DomainException {
    public static final DomainException EXCEPTION = new TokenParseFailedException();

    private TokenParseFailedException() {
        super(SecurityErrorCode.TOKEN_PARSE_FAILED);
    }
}
