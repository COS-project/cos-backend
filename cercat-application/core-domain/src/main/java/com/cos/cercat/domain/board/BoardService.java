package com.cos.cercat.domain.board;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardReader boardReader;

    public BoardService(BoardReader boardReader) {
        this.boardReader = boardReader;
    }

    public List<Board> read() {
        return boardReader.read();
    }

    public boolean isFavorite(TargetBoard targetBoard) {
        return boardReader.isFavorite(targetBoard);
    }
}
