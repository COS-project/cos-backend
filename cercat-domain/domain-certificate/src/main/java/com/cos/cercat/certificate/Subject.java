package com.cos.cercat.certificate;

public record Subject(
        long subjectId,
        Certificate certificate,
        SubjectInfo subjectInfo
) {
}
