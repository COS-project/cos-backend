package com.cos.cercat.database.mockexamresult.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class MockExamResultNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new MockExamResultNotFoundException();

    private MockExamResultNotFoundException() {
        super(GlobalErrorCode.MOCK_EXAM_RESULT_NOT_FOUND);
    }
}
