package com.cos.cercat.apis.mockExam.dto.response;

import com.cos.cercat.apis.certificate.dto.response.SubjectResponse;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.mockexam.Question;

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

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.id(),
                MockExamResponse.from(question.mockExam()),
                SubjectResponse.from(question.subject()),
                question.questionContent().questionSequence(),
                question.questionContent().questionText(),
                question.questionContent().questionImageUrl(),
                question.questionContent().questionOptions().stream()
                        .map(QuestionOptionResponse::from)
                        .toList(),
                question.questionContent().correctOption(),
                question.questionContent().score()
        );
    }
}
