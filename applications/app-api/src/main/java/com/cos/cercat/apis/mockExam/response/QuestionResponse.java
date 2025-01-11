package com.cos.cercat.apis.mockExam.response;

import com.cos.cercat.apis.certificate.response.SubjectResponse;
import com.cos.cercat.common.domain.Image;
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

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getQuestionId(),
                MockExamResponse.from(question.getMockExam()),
                SubjectResponse.from(question.getSubject()),
                question.getQuestionContent().questionSequence(),
                question.getQuestionContent().questionText(),
                question.getQuestionImage(),
                question.getQuestionOptions().stream()
                        .map(QuestionOptionResponse::from)
                        .toList(),
                question.getQuestionContent().correctOption(),
                question.getQuestionContent().score()
        );
    }
}
