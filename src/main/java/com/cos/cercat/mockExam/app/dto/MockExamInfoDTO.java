package com.cos.cercat.mockExam.app.dto;

import com.cos.cercat.mockExam.app.QuestionConverter;

import java.util.List;
import java.util.Map;

public record MockExamDto(
        MockExamInfoDto infoDto,
        Map<String, List<String>> questions
) {
    public static MockExamDto of(MockExamInfoDto infoDto, Map<String, List<String>> questions) {
        return new MockExamDto(
                infoDto,
                questions
        );
    }

    public String getCorrectAnswerViaSeq(int sequence) {
        return questions.get(QuestionConverter.CORRECT_ANSWER)
                .get(sequence - 1);
    }
}
