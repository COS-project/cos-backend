package com.cos.cercat.domain.mockexam;

import java.awt.*;

public record QuestionOption(
        int optionSequence,
        String questionImageUrl,
        String optionText
) {
}
