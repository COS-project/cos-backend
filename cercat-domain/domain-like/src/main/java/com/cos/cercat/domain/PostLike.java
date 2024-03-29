package com.cos.cercat.domain;

import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.post.Post;
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
    private User user;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public static PostLike of(User user, Post post) {
        return new PostLike(
                user,
                post
        );
    }
}
