package com.cos.cercat.mockExam.repository;

import com.cos.cercat.mockExam.domain.Question;

import java.util.List;

public interface QuestionBatchRepository {

    void batchInsert(List<Question> questions);
}
