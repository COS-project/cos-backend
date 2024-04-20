package com.cos.cercat.domain;

import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.domain.comment.PostComment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentLike {

    @EmbeddedId
    private CommentLikePK commentLikePK = new CommentLikePK();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostComment comment;

    private CommentLike(UserEntity userEntity, PostComment comment) {
        this.userEntity = userEntity;
        this.comment = comment;
    }

    public static CommentLike of(UserEntity userEntity, PostComment comment) {
        return new CommentLike(
                userEntity,
                comment
        );
    }
}
