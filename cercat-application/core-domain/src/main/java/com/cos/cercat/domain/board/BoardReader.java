package com.cos.cercat.domain.board;

import com.cos.cercat.domain.certificate.CertificateFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardReader {

    private final FavoriteBoardRepository favoriteBoardRepository;
    private final CertificateFinder certificateFinder;

    public BoardReader(FavoriteBoardRepository favoriteBoardRepository, CertificateFinder certificateFinder) {
        this.favoriteBoardRepository = favoriteBoardRepository;
        this.certificateFinder = certificateFinder;
    }



    public boolean readFavorite(TargetBoard targetBoard) {
        return favoriteBoardRepository.isFavorite(targetBoard);
    }

    public List<Board> read() {
        return certificateFinder.findAll().stream()
                .map(Board::from)
                .toList();
    }

}
