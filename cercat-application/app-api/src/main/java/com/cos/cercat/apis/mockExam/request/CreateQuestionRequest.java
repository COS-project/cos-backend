package com.cos.cercat.apis.mockExam.request;

import com.cos.cercat.mockexam.QuestionContent;
import com.cos.cercat.mockexam.QuestionOption;
import com.cos.cercat.mockexam.QuestionWithSubjectSeq;

import java.util.List;

public record CreateQuestionRequest(
        Integer subjectSeq,
        Integer questionNumber,
        String question,
        List<String> choices,
        Integer answer
) {
    public QuestionWithSubjectSeq toQuestionWithSubjectSeq(int score) {
        return new QuestionWithSubjectSeq(
                subjectSeq,
                QuestionContent.of(
                        questionNumber,
                        question,
                        answer,
                        null,
                        choices.stream()
                                .map((choice) -> QuestionOption.of(choices.indexOf(choice) + 1, choice, null))
                                .toList(),
                        score
                )
        );
    }
}
