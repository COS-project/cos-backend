package com.cos.cercat.like.domain;

import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.like.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.user.domain.User;
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
    private User user;

    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostComment comment;

    private CommentLike(User user, PostComment comment) {
        this.user = user;
        this.comment = comment;
    }

    public static CommentLike of(User user, PostComment comment) {
        return new CommentLike(
                user,
                comment
        );
    }
}
