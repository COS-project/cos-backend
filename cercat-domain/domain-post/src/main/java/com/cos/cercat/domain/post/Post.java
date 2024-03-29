package com.cos.cercat.domain.post;

import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.User;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.domain.comment.PostComments;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    protected Certificate certificate;

    @Embedded
    protected PostImages postImages = new PostImages();

    @Embedded
    protected PostComments postComments = new PostComments();

    @Enumerated(EnumType.STRING)
    protected PostType postType;

    @ColumnDefault("0")
    protected Integer likeCount = 0;

    public Post(String title, String content, User user, Certificate certificate, PostType postType, List<Image> images) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.certificate = certificate;
        this.postType = postType;
        addAllPostImages(images);
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void addComment(PostComment comment) {
        comment.setPost(this);
        this.getPostComments().addComment(comment);
    }

    public void updatePostInfo(String title, String content, List<Image> images) {
        if (Objects.nonNull(title)) this.title = title;
        if (Objects.nonNull(content)) this.content = content;
        addAllPostImages(images);
    }

    public boolean isAuthorized(User user) {
        return this.user.equals(user);
    }

    private void addAllPostImages(List<Image> images) {
        List<PostImage> postImages = images.stream()
                .map(image -> PostImage.of(this, image))
                .toList();

        this.postImages.addAll(postImages);
    }
}
