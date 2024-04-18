package com.cos.cercat.repository;


import com.cos.cercat.domain.FavoriteBoardEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBoardJpaRepository extends JpaRepository<FavoriteBoardEntity, FavoriteBoardPK> {

    List<FavoriteBoardEntity> findFavoriteBoardsByUserEntityId(Long userId);

    boolean existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK favoriteBoardPK);

}
