package com.cos.cercat.database.post.repository;


import com.cos.cercat.database.post.entity.FavoriteBoardEntity;
import com.cos.cercat.database.post.entity.embeddedId.FavoriteBoardPK;
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
