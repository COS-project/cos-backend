package com.cos.cercat.database.common.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DBErrorCode implements BaseErrorCode {
    ENTITY_TYPE_MISMATCH(404, "Entity 타입이 일치하지 않습니다."),;


    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
