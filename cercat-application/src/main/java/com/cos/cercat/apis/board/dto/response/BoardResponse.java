package com.cos.cercat.apis.board.dto.response;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.apis.certificate.dto.response.CertificateResponse;

public record BoardResponse(
        CertificateResponse certificate,
        boolean isFavorite
) {

    public static BoardResponse of(Certificate certificate, boolean isFavorite) {
        return new BoardResponse(
                CertificateResponse.from(certificate),
                isFavorite
        );
    }

}
