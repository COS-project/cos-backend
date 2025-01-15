package com.cos.cercat.security.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements BaseErrorCode {


    UNAUTHORIZED_USER(401, "인증되지 않은 사용자입니다."),
    INVALID_TOKEN(404, "유효하지 않은 토큰입니다.");

    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
