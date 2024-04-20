package com.cos.cercat.domain.mockexam;

import java.util.List;

public record QuestionContent(
        int questionSequence,
        String questionText,
        int correctOption,
        String imageUrl,
        List<QuestionOption> questionOptions,
        int score
) {
}
