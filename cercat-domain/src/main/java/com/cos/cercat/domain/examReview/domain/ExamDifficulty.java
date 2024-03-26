package com.cos.cercat.domain.examReview.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExamDifficulty {

    TOO_EASY("너무 쉬워요"),
    EASY("쉬워요"),
    NORMAL("보통이에요"),
    LITTLE_DIFFICULT("조금 어려워요"),
    TOO_DIFFICULT("어려워요");

    private final String description;

}
