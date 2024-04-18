package com.cos.cercat.domain.certificate;

public record Certificate(
        Long certificateId,
        String certificateName,
        Subjects subjects
) {
}
