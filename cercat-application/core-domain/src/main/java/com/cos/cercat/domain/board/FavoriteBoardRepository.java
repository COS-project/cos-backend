package com.cos.cercat.domain.board;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FavoriteBoardRepository {
    List<FavoriteBoard> find(Long userId);

    boolean isFavorite(TargetBoard targetBoard);
}
