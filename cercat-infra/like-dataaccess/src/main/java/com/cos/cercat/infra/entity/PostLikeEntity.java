package com.cos.cercat.infra.entity;

import com.cos.cercat.infra.entity.EmbeddedId.PostLikePK;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_like")
public class PostLikeEntity {

    @EmbeddedId
    private PostLikePK postLikePK = new PostLikePK();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = CASCADE)
    private PostEntity postEntity;

    private PostLikeEntity(UserEntity userEntity, PostEntity postEntity) {
        this.userEntity = userEntity;
        this.postEntity = postEntity;
    }

    public static PostLikeEntity of(UserEntity userEntity, PostEntity postEntity) {
        return new PostLikeEntity(
                userEntity,
                postEntity
        );
    }
}
