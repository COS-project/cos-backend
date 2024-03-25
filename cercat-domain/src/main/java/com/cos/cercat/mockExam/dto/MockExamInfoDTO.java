package com.cos.cercat.mockExam.dto;


import com.cos.cercat.mockExam.service.QuestionConvertService;

import java.util.List;
import java.util.Map;

public record MockExamInfoDTO(
        MockExamDTO mockExamDTO,
        Map<String, List<String>> questions
) {
    public static MockExamInfoDTO of(MockExamDTO dto, Map<String, List<String>> questions) {
        return new MockExamInfoDTO(
                dto,
                questions
        );
    }

    public String getCorrectAnswerViaSeq(int sequence) {
        return questions.get(QuestionConvertService.CORRECT_ANSWER)
                .get(sequence - 1);
    }
}
