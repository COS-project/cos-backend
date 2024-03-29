package com.cos.cercat.domain.comment;

import com.cos.cercat.domain.User;
import com.cos.cercat.domain.post.Post;
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
    private User user;

    @ManyToOne
    @Setter
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long parentCommentId;

    @ColumnDefault("0")
    private Integer likeCount = 0;

    @Embedded
    private ChildPostComments childPostComments = new ChildPostComments();

    @Column(length = 500)
    private String content;

    private PostComment(User user, Post post, Long parentCommentId, String content) {
        this.user = user;
        this.post = post;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public static PostComment of(User user, Post post, String content) {
        return new PostComment(user, post, null, content);
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

    public boolean isAuthorized(User user) {
        return this.user.equals(user);
    }
}
