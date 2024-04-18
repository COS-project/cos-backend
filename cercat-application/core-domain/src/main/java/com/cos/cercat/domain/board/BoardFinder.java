package com.cos.cercat.domain.board;

import com.cos.cercat.domain.certificate.CertificateFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardFinder {
    private final FavoriteBoardRepository favoriteBoardRepository;

    public BoardFinder(FavoriteBoardRepository favoriteBoardRepository) {
        this.favoriteBoardRepository = favoriteBoardRepository;
    }

    public List<FavoriteBoard> findFavorites(Long userId) {
        return favoriteBoardRepository.find(userId);
    }


}
