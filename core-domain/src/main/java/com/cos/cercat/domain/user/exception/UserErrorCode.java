package com.cos.cercat.domain.user.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    NO_PERMISSION_ERROR("USR_001", 403, "권한이 없습니다."),
    NOT_FOUND_USER("USR_002", 404, "사용자를 찾을 수 없습니다.");

    private final String code;
    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }
}
