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
}
