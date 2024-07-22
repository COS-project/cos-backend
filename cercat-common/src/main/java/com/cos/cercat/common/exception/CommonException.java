package com.cos.cercat.common.exception;

public class CommonException extends CustomException {

    public CommonException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public CommonException(BaseErrorCode errorCode) {
        super(errorCode, null);
    }

}
