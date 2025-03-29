package com.cos.cercat.apis.mockExam.response;


import com.cos.cercat.apis.certificate.response.CertificateResponse;
import com.cos.cercat.domain.mockexam.MockExam;

public record MockExamResponse(
        Long mockExamId,
        Integer examYear,
        Integer round,
        Long timeLimit,
        int maxScore,
        CertificateResponse certificate
) {


    public static MockExamResponse from(MockExam mockExam) {
        return new MockExamResponse(
                mockExam.id(),
                mockExam.mockExamSession().examYear(),
                mockExam.mockExamSession().round(),
                mockExam.timeLimit(),
                mockExam.maxScore(),
                CertificateResponse.from(mockExam.certificate())
        );
    }
}
