package com.cos.cercat.exception;

import com.cos.cercat.common.exception.BaseErrorCode;
import com.cos.cercat.common.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    NO_PERMISSION_ERROR(403, "권한이 없습니다.");

    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
