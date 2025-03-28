package com.cos.cercat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private BaseErrorCode errorCode;
    private String message;

    public CustomException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        if (message == null) {
            return errorCode.getErrorReason().message();
        } else {
            return message;
        }
    }

}
