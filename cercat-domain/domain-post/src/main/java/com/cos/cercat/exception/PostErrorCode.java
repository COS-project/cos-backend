package com.cos.cercat.exception;

import com.cos.cercat.common.exception.BaseErrorCode;
import com.cos.cercat.common.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements BaseErrorCode {

    INVALID_COMMENT_REQUEST(400, "댓글 생성 요청이 잘못되었습니다.");

    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
