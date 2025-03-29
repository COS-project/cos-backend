package com.cos.cercat.domain.examreview.exception;

import com.cos.cercat.exception.DomainException;

public class ExamReviewException extends DomainException {

    private ExamReviewException(ExamReviewErrorCode errorCode) {
        super(errorCode);
    }

    private ExamReviewException(ExamReviewErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static ExamReviewException reviewPeriodExpired() {
        return new ExamReviewException(ExamReviewErrorCode.REVIEW_PERIOD_EXPIRED);
    }

}
