package com.cos.cercat.certificate;

public record ExamTimeLimit(
        Long writtenExamTimeLimit,
        Long practicalExamTimeLimit
) {
    public static ExamTimeLimit of(Long writtenExamTimeLimit, Long practicalExamTimeLimit) {
        return new ExamTimeLimit(writtenExamTimeLimit, practicalExamTimeLimit);
    }
}