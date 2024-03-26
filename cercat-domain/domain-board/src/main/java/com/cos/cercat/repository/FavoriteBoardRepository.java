package com.cos.cercat.repository;


import com.cos.cercat.domain.FavoriteBoard;
import com.cos.cercat.domain.User;
import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBoardRepository extends JpaRepository<FavoriteBoard, FavoriteBoardPK> {

    List<FavoriteBoard> findFavoriteBoardsByUser(User user);

    boolean existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK favoriteBoardPK);

}
