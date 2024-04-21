package com.cos.cercat.apis.mockExam.dto.response;

import com.cos.cercat.domain.QuestionOptionEntity;
import com.cos.cercat.domain.mockexam.QuestionOption;

public record QuestionOptionResponse(
        Integer optionSequence,
        String optionContent,
        String optionImage
) {
    public static QuestionOptionResponse from(QuestionOptionEntity entity) {
        return new QuestionOptionResponse(
                entity.getOptionSequence(),
                entity.getOptionContent(),
                entity.getImageUrl()
        );
    }

    public static QuestionOptionResponse from(QuestionOption questionOption) {
        return new QuestionOptionResponse(
                questionOption.optionSequence(),
                questionOption.optionText(),
                questionOption.optionImageUrl()
        );
    }

}
