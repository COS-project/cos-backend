package com.cos.cercat.mockexam;


import com.cos.cercat.certificate.Certificate;

import java.util.List;

public interface MockExamRepository {

    TargetMockExam save(NewMockExam newMockExam);

    Question readQuestion(Certificate certificate,
                          MockExamSession mockExamSession,
                          int questionSequence);

    List<MockExam> find(Certificate certificate, Integer examYear);

    List<Question> findQuestions(MockExam mockExam);

    List<Integer> findExamYears(Certificate certificate);

    MockExam read(TargetMockExam targetMockExam);

    void saveQuestions(TargetMockExam targetMockExam, List<NewQuestion> newQuestions);
}
