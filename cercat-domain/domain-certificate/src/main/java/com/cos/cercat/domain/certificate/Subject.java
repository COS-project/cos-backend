package com.cos.cercat.domain.certificate;

public record Subject(
        Long subjectId,
        Certificate certificate,
        SubjectInfo subjectInfo
) {
}
