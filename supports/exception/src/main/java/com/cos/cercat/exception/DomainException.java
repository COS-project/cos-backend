package com.cos.cercat.exception;

public class DomainException extends CustomException {

    public DomainException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DomainException(BaseErrorCode errorCode) {
        super(errorCode, null);
    }
}
