package com.cos.cercat.domain.mockexam;

import java.util.List;
import java.util.Optional;

public interface MockExamCache {

    void cache(List<Question> questions);

    Optional<List<Question>> find(MockExam mockExam);

    void delete(MockExamId mockExamId);

}
