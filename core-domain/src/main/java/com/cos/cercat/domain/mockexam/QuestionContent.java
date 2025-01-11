package com.cos.cercat.domain.mockexam;

public record QuestionContent(
        int questionSequence,
        String questionText,
        int correctOption,
        int score
) {

    public static QuestionContent of(int questionSequence,
            String questionText,
            int correctOption,
            int score) {
        return new QuestionContent(
                questionSequence,
                questionText,
                correctOption,
                score
        );
    }
}
