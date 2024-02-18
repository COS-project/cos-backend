package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.mockExamResult.domain.MockExamResult;

public interface MockExamResultBatchRepository {

    long batchInsert(MockExamResult mockExamResult);
}
