package com.cos.cercat.repository;

import com.cos.cercat.domain.MockExamResult;

public interface MockExamResultBatchRepository {

    long batchInsert(MockExamResult mockExamResult);
}
