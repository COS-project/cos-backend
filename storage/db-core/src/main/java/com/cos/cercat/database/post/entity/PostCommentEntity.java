package com.cos.cercat.database.post.entity;

import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.post.CommentContent;
import com.cos.cercat.domain.post.DateTime;
import com.cos.cercat.domain.post.PostComment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_comment")
public class PostCommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Setter
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostEntity postEntity;

    private Long parentCommentId;

    @ColumnDefault("0")
    private Integer likeCount = 0;

    @Column(length = 500)
    private String content;

    public PostCommentEntity(Long id,
                             UserEntity userEntity,
                             PostEntity postEntity,
                             Long parentCommentId,
                             Integer likeCount,
                             String content,
                             LocalDateTime createdAt) {
        this.id = id;
        this.userEntity = userEntity;
        this.postEntity = postEntity;
        this.parentCommentId = parentCommentId;
        this.likeCount = likeCount;
        this.content = content;
        this.createdAt = createdAt;
    }

    private PostCommentEntity(UserEntity userEntity,
                              PostEntity postEntity,
                              Long parentCommentId,
                              String content) {
        this.userEntity = userEntity;
        this.postEntity = postEntity;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public static PostCommentEntity of(UserEntity userEntity,
                                PostEntity postEntity,
                                Long parentCommentId,
                                String content) {
        return new PostCommentEntity(userEntity, postEntity, parentCommentId, content);
    }

    public PostComment toDomain() {
        return new PostComment(
                id,
                userEntity.toDomain(),
                new CommentContent(parentCommentId, content),
                postEntity.getId(),
                likeCount,
                new DateTime(createdAt, modifiedAt)
        );
    }

    public static PostCommentEntity of(PostComment postComment, PostEntity postEntity) {
        return new PostCommentEntity(
                postComment.getId(),
                UserEntity.from(postComment.getUser()),
                postEntity,
                postComment.getContent().parentId(),
                postComment.getLikeCount(),
                postComment.getContent().content(),
                postComment.getDateTime().createdAt()
        );
    }

}
