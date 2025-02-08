package com.cos.cercat.domain.postsearch.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostSearchErrorCode implements BaseErrorCode {

    POST_SEARCH_ERROR_CODE(404, "search post not founded"),
    ;

    private final Integer status;
    private final String message;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
