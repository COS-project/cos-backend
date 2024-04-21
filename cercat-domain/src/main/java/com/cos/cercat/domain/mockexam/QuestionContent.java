package com.cos.cercat.domain.mockexam;

import java.util.List;

public record QuestionContent(
        int questionSequence,
        String questionText,
        int correctOption,
        String questionImageUrl,
        List<QuestionOption> questionOptions,
        int score
) {
}
