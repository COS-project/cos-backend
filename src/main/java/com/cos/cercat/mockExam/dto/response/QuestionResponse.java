package com.cos.cercat.mockExam.dto.response;

import com.cos.cercat.certificate.dto.response.SubjectResponse;
import com.cos.cercat.mockExam.domain.Question;

import java.util.List;

public record QuestionResponse(
        Long questionId,
        MockExamResponse mockExam,
        SubjectResponse subject,
        int questionSeq,
        String questionText,
        String questionImage,
        List<QuestionOptionResponse> questionOptions,
        int correct_option,
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
                entity.getCorrect_option(),
                entity.getScore()
        );
    }
}
