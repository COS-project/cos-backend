package com.cos.cercat.database.mockexam.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class MockExamNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new MockExamNotFoundException();

    private MockExamNotFoundException() {
        super(GlobalErrorCode.MOCK_EXAM_NOT_FOUND);
    }
}
