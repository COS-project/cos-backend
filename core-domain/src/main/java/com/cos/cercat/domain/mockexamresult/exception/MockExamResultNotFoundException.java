package com.cos.cercat.domain.mockexamresult.exception;

import com.cos.cercat.exception.DomainException;

public class MockExamResultNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new MockExamResultNotFoundException();

    private MockExamResultNotFoundException() {
        super(MockExamResultErrorCode.MOCK_EXAM_RESULT_NOT_FOUND);
    }
}
