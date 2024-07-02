package com.cos.cercat.certificate;

public record Subject(
        Long subjectId,
        Certificate certificate,
        SubjectInfo subjectInfo
) {
}
