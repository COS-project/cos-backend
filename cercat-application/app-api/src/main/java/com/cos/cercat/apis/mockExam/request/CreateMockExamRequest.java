package com.cos.cercat.apis.mockExam.request;


import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.mockexam.QuestionWithSubjectSeq;

import java.util.List;

public record CreateMockExamRequest(
        Integer examYear,
        Integer round,
        List<CreateQuestionRequest> questions,
        Long timeLimit,
        Integer score
) {

    public MockExamSession toExamSession() {
        return new MockExamSession(examYear, round);
    }

    public List<QuestionWithSubjectSeq> toContent() {
        return questions.stream()
                .map(question -> question.toQuestionWithSubjectSeq(score))
                .toList();
    }
}
