package com.cos.cercat.apis.mockExam.request;

import com.cos.cercat.domain.mockexam.QuestionContent;
import com.cos.cercat.domain.mockexam.QuestionOption;
import com.cos.cercat.domain.mockexam.QuestionWithSubjectSeq;

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
                null,
                choices.stream()
                        .map((choice) -> QuestionOption.of(choices.indexOf(choice) + 1, choice,
                                null))
                        .toList(),
                QuestionContent.of(
                        questionNumber,
                        question,
                        answer,
                        score
                )
        );
    }
}
