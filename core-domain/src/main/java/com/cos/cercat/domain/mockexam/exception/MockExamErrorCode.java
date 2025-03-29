package com.cos.cercat.domain.mockexam.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MockExamErrorCode implements BaseErrorCode {

    MOCK_EXAM_NOT_FOUND("MEX_001", 404, "mock exam not founded"),
    QUESTION_NOT_FOUND("MEX_002", 404, "question not founded"),

    ;

    private final String code;
    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }
}
