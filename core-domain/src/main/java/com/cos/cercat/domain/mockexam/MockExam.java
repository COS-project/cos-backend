package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Certificate;

public record MockExam(
        long id,
        MockExamSession mockExamSession,
        long timeLimit,
        Certificate certificate
) {

}
