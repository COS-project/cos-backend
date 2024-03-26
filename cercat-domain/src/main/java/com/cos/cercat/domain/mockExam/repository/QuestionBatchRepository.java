package com.cos.cercat.domain.mockExam.repository;

import com.cos.cercat.domain.mockExam.domain.Question;

import java.util.List;

public interface QuestionBatchRepository {

    void batchInsert(List<Question> questions);
}
