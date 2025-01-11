package com.cos.cercat.domain.mockexamresult;

public record TargetUserAnswer(
        Long userAnswerId
) {
    public static TargetUserAnswer from(Long userAnswerId) {
        return new TargetUserAnswer(userAnswerId);
    }
}
