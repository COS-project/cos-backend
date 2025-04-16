package com.cos.cercat.domain.like.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements BaseErrorCode {

    ALREADY_LIKED("LKE_001", 400, "이미 좋아요 상태입니다."),
    ALREADY_NOT_LIKED("LKE_002", 400, "이미 좋아요 해제 상태입니다."),
    ;

    private final String code;
    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }
}
