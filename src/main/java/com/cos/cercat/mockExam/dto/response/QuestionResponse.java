package com.cos.cercat.mockExam.dto.response;

import com.cos.cercat.mockExam.domain.Question;

public record QuestionResponse(
        Long questionId,
        SubjectResponse subject,
        int questionSeq,
        String question_text,
        String option_1,
        String option_2,
        String option_3,
        String option_4,
        int correct_option,
        int score
) {
    public static QuestionResponse from(Question entity) {
        return new QuestionResponse(
                entity.getId(),
                SubjectResponse.from(entity.getSubject()),
                entity.getQuestionSeq(),
                entity.getQuestion_text(),
                entity.getOption_1(),
                entity.getOption_2(),
                entity.getOption_3(),
                entity.getOption_4(),
                entity.getCorrect_option(),
                entity.getScore()
        );
    }
}
