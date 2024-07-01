package com.cos.cercat.apis.mockExam.request;

import com.cos.cercat.mockexam.NewQuestion;
import com.cos.cercat.mockexam.QuestionContent;
import com.cos.cercat.mockexam.QuestionOption;

import java.util.List;

public record CreateQuestionRequest(
        Integer subjectSeq,
        Integer questionNumber,
        String question,
        List<String> choices,
        Integer answer
) {
    public NewQuestion toNewQuestion(int score) {
        return new NewQuestion(
                subjectSeq,
                QuestionContent.of(
                        questionNumber,
                        question,
                        answer,
                        null,
                        choices.stream()
                                .map((choice) -> QuestionOption.of(choices.indexOf(choice), choice, null))
                                .toList(),
                        score
                )
        );
    }
}
