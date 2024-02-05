package com.cos.cercat.favoriteBoard.repository;

import com.cos.cercat.favoriteBoard.domain.FavoriteBoard;
import com.cos.cercat.favoriteBoard.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBoardRepository extends JpaRepository<FavoriteBoard, FavoriteBoardPK> {

    List<FavoriteBoard> findFavoriteBoardsByUser(User user);

    boolean existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK favoriteBoardPK);

}
