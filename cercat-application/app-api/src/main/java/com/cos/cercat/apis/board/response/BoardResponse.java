package com.cos.cercat.apis.board.response;

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
