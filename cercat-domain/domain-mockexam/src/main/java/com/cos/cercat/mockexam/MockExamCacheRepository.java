package com.cos.cercat.mockexam;

import java.util.List;
import java.util.Optional;

public interface MockExamCacheRepository {

    void setQuestions(List<Question> questions);

    Optional<List<Question>> getQuestions(TargetMockExam targetMockExam);

    void deleteQuestions(TargetMockExam targetMockExam);

}
