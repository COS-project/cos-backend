package com.cos.cercat.mockexam;


import com.cos.cercat.certificate.Certificate;

public record MockExam(
        long id,
        MockExamSession mockExamSession,
        long timeLimit,
        Certificate certificate
) {
}
