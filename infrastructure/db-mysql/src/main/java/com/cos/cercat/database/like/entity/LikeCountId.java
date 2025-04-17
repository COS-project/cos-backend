package com.cos.cercat.database.like.entity;

import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LikeCountId {

    private Long targetId;
    @Enumerated(EnumType.STRING)
    private LikeTargetType targetType;

    public static LikeCountId from(LikeTarget likeTarget) {
        return new LikeCountId(likeTarget.targetId(), likeTarget.targetType());
    }

}
