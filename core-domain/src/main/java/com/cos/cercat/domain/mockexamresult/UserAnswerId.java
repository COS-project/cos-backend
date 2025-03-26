package com.cos.cercat.domain.mockexamresult;

public record UserAnswerId(
        Long userAnswerId
) {
    public static UserAnswerId from(Long userAnswerId) {
        return new UserAnswerId(userAnswerId);
    }
}
