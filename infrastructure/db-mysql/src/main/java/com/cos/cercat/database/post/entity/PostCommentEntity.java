package com.cos.cercat.database.post.entity;

import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.post.CommentContent;
import com.cos.cercat.domain.post.DateTime;
import com.cos.cercat.domain.post.PostComment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Table(name = "post_comment")
public class PostCommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private Long postId;

    private Long parentCommentId;

    @Column(length = 500)
    private String content;

    public static PostCommentEntity from(PostComment postComment) {
        return PostCommentEntity.builder()
                .id(postComment.getId())
                .userEntity(UserEntity.from(postComment.getOwner()))
                .postId(postComment.getPostId())
                .parentCommentId(postComment.getContent().parentId())
                .content(postComment.getContent().content())
                .createdAt(postComment.getDateTime().createdAt())
                .modifiedAt(postComment.getDateTime().modifiedAt())
                .build();
    }

    public PostComment toDomain() {
        return PostComment.builder()
                .id(id)
                .commenter(userEntity.toDomain())
                .postId(postId)
                .content(new CommentContent(parentCommentId, content))
                .dateTime(new DateTime(createdAt, modifiedAt))
                .build();
    }

}
