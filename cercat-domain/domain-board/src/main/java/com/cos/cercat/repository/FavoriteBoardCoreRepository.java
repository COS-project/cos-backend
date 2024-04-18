package com.cos.cercat.repository;

import com.cos.cercat.domain.FavoriteBoardEntity;
import com.cos.cercat.domain.board.FavoriteBoard;
import com.cos.cercat.domain.board.FavoriteBoardRepository;
import com.cos.cercat.domain.board.TargetBoard;
import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteBoardCoreRepository implements FavoriteBoardRepository {

    private final FavoriteBoardJpaRepository favoriteBoardJpaRepository;

    public FavoriteBoardCoreRepository(FavoriteBoardJpaRepository favoriteBoardJpaRepository) {
        this.favoriteBoardJpaRepository = favoriteBoardJpaRepository;
    }

    @Override
    public List<FavoriteBoard> find(Long userId) {
        return favoriteBoardJpaRepository.findFavoriteBoardsByUserEntityId(userId).stream()
                .map(FavoriteBoardEntity::toFavoriteBoard)
                .toList();
    }

    @Override
    public boolean isFavorite(TargetBoard targetBoard) {
        return favoriteBoardJpaRepository.existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK.from(targetBoard));
    }
}
