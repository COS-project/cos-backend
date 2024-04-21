package com.cos.cercat.domain.comment;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.DateTime;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostCommentWithChild;
import com.cos.cercat.domain.post.PostEntity;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "post_comment")
public class PostCommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @Setter
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostEntity postEntity;

    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long parentCommentId;

    @ColumnDefault("0")
    private Integer likeCount = 0;

    @Embedded
    private ChildPostComments childPostComments = new ChildPostComments();

    @Column(length = 500)
    private String content;

    private PostCommentEntity(UserEntity userEntity, PostEntity postEntity, Long parentCommentId, String content) {
        this.userEntity = userEntity;
        this.postEntity = postEntity;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public PostComment toDomain() {
        return new PostComment(
                this.id,
                this.userEntity.toDomain(),
                this.content,
                this.parentCommentId,
                this.likeCount,
                new DateTime(createdAt, modifiedAt),
                new ArrayList<>()
        );
    }

    public static PostCommentEntity of(UserEntity userEntity, PostEntity postEntity, String content) {
        return new PostCommentEntity(userEntity, postEntity, null, content);
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void addChildComment(PostCommentEntity child) {
        child.setParentCommentId(this.getId());
        this.getChildPostComments().addChildComment(child);
    }

    public boolean isAuthorized(UserEntity userEntity) {
        return this.userEntity.equals(userEntity);
    }
}
