package com.cos.cercat.domain.post;


import com.cos.cercat.domain.certificate.Certificate;

public record Board(
        Long certificateId,
        String boardName
) {
    public static Board from(Certificate certificate) {
        return new Board(certificate.id(), certificate.certificateName());
    }
}
