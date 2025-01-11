package com.cos.cercat.apis.global.security.exception;

import com.cos.cercat.common.exception.BaseErrorCode;
import com.cos.cercat.common.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements BaseErrorCode {


    UNAUTHORIZED_USER(401, "인증되지 않은 사용자입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    UNAUTHORIZED_ERROR(401, "인증되지 않은 사용자입니다.");

    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
