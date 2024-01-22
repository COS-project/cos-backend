package com.cos.cercat.comment.domain;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


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
    private Post post;

    @Setter
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
