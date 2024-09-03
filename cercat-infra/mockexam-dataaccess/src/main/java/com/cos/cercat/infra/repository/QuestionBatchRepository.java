package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.QuestionEntity;

import java.util.List;

public interface QuestionBatchRepository {

    void batchInsert(List<QuestionEntity> questionEntities);
}
