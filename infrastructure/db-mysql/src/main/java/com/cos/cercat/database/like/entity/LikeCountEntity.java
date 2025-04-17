package com.cos.cercat.database.like.entity;

import com.cos.cercat.domain.like.LikeCount;
import com.cos.cercat.domain.like.LikeTarget;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(
        name = "like_count",
        indexes = {
                @Index(name = "idx_like_type_id_count", columnList = "target_type, target_id, count")
        }
)
public class LikeCountEntity {

    @EmbeddedId
    private LikeCountId id;
    private Long count;

    public LikeCountEntity(LikeCount likeCount) {
        this.id = LikeCountId.from(likeCount.likeTarget());
        this.count = likeCount.value();
    }

    public static LikeCountEntity from(LikeCount likeCount) {
        return new LikeCountEntity(likeCount);
    }

    public LikeCount toDomain() {
        return new LikeCount(new LikeTarget(id.getTargetId(), id.getTargetType()), count);
    }
}

