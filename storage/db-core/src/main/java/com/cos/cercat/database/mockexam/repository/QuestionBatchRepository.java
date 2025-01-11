package com.cos.cercat.database.mockexam.repository;

import com.cos.cercat.database.mockexam.entity.QuestionEntity;

import java.util.List;

public interface QuestionBatchRepository {

    void batchInsert(List<QuestionEntity> questionEntities);
}
