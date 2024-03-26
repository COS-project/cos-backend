package com.cos.cercat.domain.board.repository;

import com.cos.cercat.domain.board.domain.FavoriteBoard;
import com.cos.cercat.domain.board.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBoardRepository extends JpaRepository<FavoriteBoard, FavoriteBoardPK> {

    List<FavoriteBoard> findFavoriteBoardsByUser(User user);

    boolean existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK favoriteBoardPK);

}
