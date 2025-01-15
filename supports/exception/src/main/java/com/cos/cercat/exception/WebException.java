package com.cos.cercat.exception;

public class WebException extends CustomException {
    public WebException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public WebException(BaseErrorCode errorCode) {
        super(errorCode, null);
    }
}
