package com.cos.cercat.domain.mockexam;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public final class QuestionOption {

    private int optionSequence;
    private String optionText;
    private String optionImageUrl;

    public static QuestionOption of(int optionSequence, String optionText, String optionImageUrl) {
        return new QuestionOption(optionSequence, optionText, optionImageUrl);
    }

    public void setImageUrl(String imageUrl) {
        this.optionImageUrl = imageUrl;
    }

}
