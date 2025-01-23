package com.cos.cercat.database.like.repository;

import com.cos.cercat.database.like.entity.LikeCountEntity;
import com.cos.cercat.database.like.entity.LikeCountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCountJpaRepository extends JpaRepository<LikeCountEntity, LikeCountId> {

}
