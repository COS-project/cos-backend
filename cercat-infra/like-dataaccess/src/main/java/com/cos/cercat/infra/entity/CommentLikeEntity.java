package com.cos.cercat.infra.entity;

import com.cos.cercat.infra.entity.EmbeddedId.CommentLikePK;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comment_like")
public class CommentLikeEntity {

    @EmbeddedId
    private CommentLikePK commentLikePK = new CommentLikePK();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @OnDelete(action = CASCADE)
    private PostCommentEntity comment;

    private CommentLikeEntity(UserEntity userEntity, PostCommentEntity comment) {
        this.userEntity = userEntity;
        this.comment = comment;
    }

    public static CommentLikeEntity of(UserEntity userEntity, PostCommentEntity comment) {
        return new CommentLikeEntity(
                userEntity,
                comment
        );
    }
}
