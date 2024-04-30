package com.cos.cercat.repository;


import com.cos.cercat.domain.FavoriteBoardEntity;
import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteBoardJpaRepository extends JpaRepository<FavoriteBoardEntity, FavoriteBoardPK> {

    @Modifying
    @Query("""
            delete from FavoriteBoardEntity f
            where f.favoriteBoardPK.userEntity.id = :userId
            and f.favoriteBoardPK.certificateEntity.id = :certificateId
            """)
    void deleteById(Long userId, Long certificateId);

    boolean existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK favoriteBoardPK);

}
