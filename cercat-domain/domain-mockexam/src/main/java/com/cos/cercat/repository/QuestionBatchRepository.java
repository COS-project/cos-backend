package com.cos.cercat.repository;

import com.cos.cercat.domain.Question;

import java.util.List;

public interface QuestionBatchRepository {

    void batchInsert(List<Question> questions);
}
