package com.cos.cercat.database.like.entity;

import com.cos.cercat.domain.like.LikeCount;
import com.cos.cercat.domain.like.LikeTarget;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LikeCountEntity {

    @EmbeddedId
    private LikeCountId id;
    private Long count;

    public LikeCountEntity(LikeCount likeCount) {
        this.id = LikeCountId.from(likeCount.likeTarget());
        this.count = likeCount.count();
    }

    public static LikeCountEntity from(LikeCount likeCount) {
        return new LikeCountEntity(likeCount);
    }

    public LikeCount toDomain() {
        return new LikeCount(new LikeTarget(id.targetId(), id.targetType()), count);
    }
}

