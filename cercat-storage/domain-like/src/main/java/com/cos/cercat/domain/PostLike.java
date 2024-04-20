package com.cos.cercat.domain;

import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.post.PostEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "from")
@Getter
public class PostLike {

    @EmbeddedId
    private PostLikePK postLikePK = new PostLikePK();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    private PostLike(UserEntity userEntity, PostEntity postEntity) {
        this.userEntity = userEntity;
        this.postEntity = postEntity;
    }

    public static PostLike of(UserEntity userEntity, PostEntity postEntity) {
        return new PostLike(
                userEntity,
                postEntity
        );
    }
}
