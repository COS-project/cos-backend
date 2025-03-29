package com.cos.cercat.domain.examreview.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExamReviewErrorCode implements BaseErrorCode {

    REVIEW_PERIOD_EXPIRED("EXR_001", 400, "따끈 후기 작성 기간이 아닙니다."),
    ;

    private final String code;
    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }

}
