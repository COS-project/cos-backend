package com.cos.cercat.apis.mockExam.response;


import com.cos.cercat.domain.mockexam.QuestionOption;

public record QuestionOptionResponse(
        Integer optionSequence,
        String optionContent,
        String optionImage
) {


    public static QuestionOptionResponse from(QuestionOption questionOption) {
        return new QuestionOptionResponse(
                questionOption.getOptionSequence(),
                questionOption.getOptionText(),
                questionOption.getOptionImageUrl()
        );
    }

}
