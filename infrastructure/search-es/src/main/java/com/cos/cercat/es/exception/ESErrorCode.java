package com.cos.cercat.es.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ESErrorCode implements BaseErrorCode {

    ES_SEARCH_ERROR(500, "엘라스틱 서치 서버에러입니다. 강지원에게 문의해주세요");
    ;

    private final Integer status;
    private final String message;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
