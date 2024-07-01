package com.cos.cercat.apis.mockExam.request;


import com.cos.cercat.mockexam.NewQuestion;

import java.util.List;

public record CreateMockExamRequest(
        Integer examYear,
        Integer round,
        List<CreateQuestionRequest> questions,
        Long timeLimit,
        Integer score
) {
    public List<NewQuestion> toNewQuestions() {
        return questions.stream()
                .map(question -> question.toNewQuestion(score))
                .toList();
    }
}
