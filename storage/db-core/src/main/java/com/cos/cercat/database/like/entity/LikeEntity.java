package com.cos.cercat.database.like.entity;

import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "likes")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private LikeTargetType likeTargetType;

    public static LikeEntity of(User user, LikeTarget likeTarget) {
        return new LikeEntity(null, user.getId(), likeTarget.targetId(), likeTarget.targetType());
    }
}
