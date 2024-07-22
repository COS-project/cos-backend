package com.cos.cercat.common.exception;

public class WebException extends CustomException {
    public WebException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public WebException(BaseErrorCode errorCode) {
        super(errorCode, null);
    }
}
