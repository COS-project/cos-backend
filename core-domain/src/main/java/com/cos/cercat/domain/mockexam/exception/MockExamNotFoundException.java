package com.cos.cercat.domain.mockexam.exception;

import com.cos.cercat.exception.DomainException;
import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class MockExamNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new MockExamNotFoundException();

    private MockExamNotFoundException() {
        super(MockExamErrorCode.MOCK_EXAM_NOT_FOUND);
    }
}
