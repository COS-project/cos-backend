package com.cos.cercat.apis.board.dto.response;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.apis.certificate.dto.response.CertificateResponse;
import com.cos.cercat.domain.board.Board;

public record BoardResponse(
        Long certificateId,
        String boardName,
        boolean isFavorite
) {

    public static BoardResponse of(Board board, boolean isFavorite) {
        return new BoardResponse(
                board.certificateId(),
                board.boardName(),
                isFavorite
        );
    }

}
