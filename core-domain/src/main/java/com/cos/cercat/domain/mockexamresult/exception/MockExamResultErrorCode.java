package com.cos.cercat.domain.mockexamresult.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MockExamResultErrorCode implements BaseErrorCode {

    MOCK_EXAM_RESULT_NOT_FOUND(404, "mock exam result not founded"),
    USER_ANSWER_NOT_FOUND(404, "user answer not founded"),
    ;

    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
