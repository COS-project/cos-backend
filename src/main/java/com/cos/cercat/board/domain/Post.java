package com.cos.cercat.board.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.domain.PostComments;
import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorColumn
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;

    protected String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = true)
    @JoinColumn(name = "certificate_id")
    protected Certificate certificate;

    @Embedded
    protected PostImages postImages = new PostImages();

    @Embedded
    protected PostComments postComments = new PostComments();

    @ColumnDefault("0")
    protected Integer likeCount = 0;

    public Post(String title, String content, User user, Certificate certificate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.certificate = certificate;
    }

    public void addAllPostImages(List<Image> images) {
        List<PostImage> postImages = images.stream()
                .map(image -> PostImage.of(this, image))
                .toList();

        this.postImages.addAllImages(postImages);
    }

    public void addComment(PostComment comment) {
        comment.setPost(this);
        this.getPostComments().addComment(comment);
    }

    public void updatePostInfo(String title, String content, List<Image> images) {
        this.title = title;
        this.content = content;
        addAllPostImages(images);
    }

    public boolean isAuthorized(User user) {
        return this.user.equals(user);
    }

}
