package com.cos.cercat.domain.comment;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.PostEntity;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostComment extends BaseTimeEntity {

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

    private PostComment(UserEntity userEntity, PostEntity postEntity, Long parentCommentId, String content) {
        this.userEntity = userEntity;
        this.postEntity = postEntity;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public static PostComment of(UserEntity userEntity, PostEntity postEntity, String content) {
        return new PostComment(userEntity, postEntity, null, content);
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void addChildComment(PostComment child) {
        child.setParentCommentId(this.getId());
        this.getChildPostComments().addChildComment(child);
    }

    public boolean isAuthorized(UserEntity userEntity) {
        return this.userEntity.equals(userEntity);
    }
}