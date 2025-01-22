package com.cos.cercat.database.like.entity;

import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LikeCountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long targetId;
    private LikeTargetType likeTargetType;
    private Long count;

    public static LikeCountEntity from(LikeTarget likeTarget) {
        return new LikeCountEntity(null, likeTarget.targetId(), likeTarget.targetType(), 0L);
    }

}
