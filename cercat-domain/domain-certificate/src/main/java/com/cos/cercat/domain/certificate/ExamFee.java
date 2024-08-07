package com.cos.cercat.domain.certificate;

public record ExamFee(
        Integer writtenExamFee,
        Integer practicalExamFee
) {
    public static ExamFee of(Integer writtenExamFee, Integer practicalExamFee) {
        return new ExamFee(writtenExamFee, practicalExamFee);
    }
}