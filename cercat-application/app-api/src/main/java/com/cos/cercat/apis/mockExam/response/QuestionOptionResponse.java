package com.cos.cercat.apis.mockExam.response;


import com.cos.cercat.mockexam.QuestionOption;

public record QuestionOptionResponse(
        Integer optionSequence,
        String optionContent,
        String optionImage
) {


    public static QuestionOptionResponse from(QuestionOption questionOption) {
        return new QuestionOptionResponse(
                questionOption.optionSequence(),
                questionOption.optionText(),
                questionOption.optionImageUrl()
        );
    }

}
