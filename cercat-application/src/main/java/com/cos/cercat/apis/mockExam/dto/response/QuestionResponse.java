package com.cos.cercat.apis.mockExam.dto.response;

import com.cos.cercat.apis.certificate.dto.response.SubjectResponse;
import com.cos.cercat.domain.mockExam.domain.Question;

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
    public static QuestionResponse from(Question entity) {
        return new QuestionResponse(
                entity.getId(),
                MockExamResponse.from(entity.getMockExam()),
                SubjectResponse.from(entity.getSubject()),
                entity.getQuestionSeq(),
                entity.getQuestionText(),
                entity.getImageUrl(),
                entity.getQuestionOptions().getAll().stream()
                        .map(QuestionOptionResponse::from)
                        .toList(),
                entity.getCorrectOption(),
                entity.getScore()
        );
    }
}
