package com.cos.cercat.board;


import com.cos.cercat.certificate.Certificate;

public record Board(
        Long certificateId,
        String boardName
) {
    public static Board from(Certificate certificate) {
        return new Board(certificate.id(), certificate.certificateName());
    }
}
