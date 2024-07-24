package com.cos.cercat.domain.mockexam;

import java.util.List;
import java.util.Optional;

public interface MockExamCacheRepository {

    void setQuestions(List<Question> questions);

    Optional<List<Question>> getQuestions(MockExam mockExam);

    void deleteQuestions(TargetMockExam targetMockExam);

}
