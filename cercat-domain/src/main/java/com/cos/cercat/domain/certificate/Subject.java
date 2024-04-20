package com.cos.cercat.domain.certificate;

public record Subject(
        long subjectId,
        Certificate certificate,
        SubjectInfo subjectInfo
) {
}
