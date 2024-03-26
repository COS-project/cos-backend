package com.cos.cercat.apis.mockExam.dto.response;

import com.cos.cercat.domain.mockExam.domain.QuestionOption;

public record QuestionOptionResponse(
        Integer optionSequence,
        String optionContent,
        String optionImage
) {
    public static QuestionOptionResponse from(QuestionOption entity) {
        return new QuestionOptionResponse(
                entity.getOptionSequence(),
                entity.getOptionContent(),
                entity.getImageUrl()
        );
    }

}
