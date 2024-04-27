package com.cos.cercat.apis.mockExam.response;


import com.cos.cercat.apis.certificate.response.CertificateResponse;
import com.cos.cercat.mockexam.MockExam;

public record MockExamResponse(
        Long MockExamId,
        Integer examYear,
        Integer round,
        Long timeLimit,
        CertificateResponse certificate
) {


    public static MockExamResponse from(MockExam mockExam) {
        return new MockExamResponse(
                mockExam.id(),
                mockExam.mockExamSession().examYear(),
                mockExam.mockExamSession().round(),
                mockExam.timeLimit(),
                CertificateResponse.from(mockExam.certificate())
        );
    }
}
