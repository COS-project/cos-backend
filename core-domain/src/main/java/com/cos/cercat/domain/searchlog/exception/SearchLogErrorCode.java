package com.cos.cercat.domain.searchlog.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchLogErrorCode implements BaseErrorCode {

    ;

    private final String code;
    private final Integer status;
    private final String message;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }
}
