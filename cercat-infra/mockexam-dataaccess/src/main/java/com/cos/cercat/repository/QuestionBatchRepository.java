package com.cos.cercat.repository;

import com.cos.cercat.domain.QuestionEntity;

import java.util.List;

public interface QuestionBatchRepository {

    void batchInsert(List<QuestionEntity> questionEntities);
}
