package com.cos.cercat.domain.mockExamResult.repository;

import com.cos.cercat.domain.mockExamResult.domain.MockExamResult;

public interface MockExamResultBatchRepository {

    long batchInsert(MockExamResult mockExamResult);
}
