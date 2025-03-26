package com.cos.cercat.domain.post;


import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateId;

public record Board(
        CertificateId certificateId,
        String boardName
) {
    public static Board from(Certificate certificate) {
        return new Board(certificate.id(), certificate.certificateName());
    }
}
