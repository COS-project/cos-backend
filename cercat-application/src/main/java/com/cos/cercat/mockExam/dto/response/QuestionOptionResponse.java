package com.cos.cercat.mockExam.dto.response;

import com.cos.cercat.mockExam.domain.QuestionOption;

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
