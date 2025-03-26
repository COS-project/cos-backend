package com.cos.cercat.domain.mockexam;


import com.cos.cercat.domain.certificate.Certificate;

import java.util.List;

public interface MockExamRepository {

    MockExamId save(NewMockExam newMockExam);

    Question readQuestion(Certificate certificate,
            MockExamSession mockExamSession,
            int questionSequence);

    List<MockExam> find(Certificate certificate, Integer examYear);

    List<Question> findQuestions(MockExam mockExam);

    List<Integer> findExamYears(Certificate certificate);

    MockExam find(MockExamId mockExamId);

    void saveQuestions(MockExamId mockExamId, List<NewQuestion> newQuestions);

    Question findById(Long questionId);

    void updateQuestion(Question question);
}
