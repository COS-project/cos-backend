package com.cos.cercat.apis.mockExam.response;

import com.cos.cercat.apis.certificate.response.SubjectResponse;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.mockexam.Question;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuestionResponse(
        Long questionId,
        MockExamResponse mockExam,
        SubjectResponse subject,
        int questionSeq,
        String questionText,
        Image questionImage,
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
                entity.getQuestionImageEntity().toImage(),
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
                question.questionContent().questionImage(),
                question.questionContent().questionOptions().stream()
                        .map(QuestionOptionResponse::from)
                        .toList(),
                question.questionContent().correctOption(),
                question.questionContent().score()
        );
    }
}
