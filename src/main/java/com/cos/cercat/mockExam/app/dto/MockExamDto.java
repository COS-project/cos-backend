package com.cos.cercat.mockExam.app.dto;

import com.cos.cercat.mockExam.app.QuestionConverter;

import java.util.List;
import java.util.Map;

public record MockExamDto(
        String description,
        Map<String, List<String>> questions
) {
    public static MockExamDto of(String description, Map<String, List<String>> questions) {
        return new MockExamDto(
                description,
                questions
        );
    }

    public String getCorrectAnswerViaSeq(int sequence) {
        return questions.get(QuestionConverter.CORRECT_ANSWER)
                .get(sequence - 1);
    }
}
