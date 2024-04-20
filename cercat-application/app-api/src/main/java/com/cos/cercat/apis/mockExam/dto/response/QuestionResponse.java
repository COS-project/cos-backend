package com.cos.cercat.apis.mockExam.dto.response;

import com.cos.cercat.apis.certificate.dto.response.SubjectResponse;
import com.cos.cercat.domain.QuestionEntity;

import java.util.List;

public record QuestionResponse(
        Long questionId,
        MockExamResponse mockExam,
        SubjectResponse subject,
        int questionSeq,
        String questionText,
        String questionImage,
        List<QuestionOptionResponse> questionOptions,
        int correctOption,
        int score
) {
    public static QuestionResponse from(QuestionEntity entity) {
        return new QuestionResponse(
                entity.getId(),
                MockExamResponse.from(entity.getMockExamEntity()),
                SubjectResponse.from(entity.getSubjectEntity()),
                entity.getQuestionSeq(),
                entity.getQuestionText(),
                entity.getImageUrl(),
                entity.getQuestionOptions().stream()
                        .map(QuestionOptionResponse::from)
                        .toList(),
                entity.getCorrectOption(),
                entity.getScore()
        );
    }
}
