package com.cos.cercat.mockexam;

import com.cos.cercat.common.domain.Image;

import java.util.List;

public record QuestionContent(
        int questionSequence,
        String questionText,
        int correctOption,
        Image questionImage,
        List<QuestionOption> questionOptions,
        int score
) {
    public static QuestionContent of(int questionSequence,
                                     String questionText,
                                     int correctOption,
                                     Image questionImage,
                                     List<QuestionOption> questionOptions,
                                     int score) {
        return new QuestionContent(
                questionSequence,
                questionText,
                correctOption,
                questionImage,
                questionOptions,
                score
        );
    }
}
