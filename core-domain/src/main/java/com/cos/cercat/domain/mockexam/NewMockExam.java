package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Certificate;

public record NewMockExam(
        MockExamInfo info,
        Certificate certificate
) {

    public static NewMockExam of(
            MockExamInfo info,
            Certificate certificate
    ) {
        return new NewMockExam(
                info,
                certificate
        );
    }
}
