package com.cos.cercat.database.like.repository;

import com.cos.cercat.database.like.entity.LikeCountEntity;
import com.cos.cercat.database.like.entity.LikeCountId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeCountJpaRepository extends JpaRepository<LikeCountEntity, LikeCountId>, LikeCountJpaBatchRepository {

    @Query("""
            SELECT l
            FROM LikeCountEntity l
            WHERE l.id IN :list
          """)
    List<LikeCountEntity> findByIds(List<LikeCountId> list);
}
