package com.cos.cercat.board.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorColumn
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @Embedded
    private PostImages postImages = new PostImages();

    private Integer likeCount;

    public Post(String title, String content, User user, Certificate certificate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.certificate = certificate;
    }

    public void addPostImage(Image image) {
        this.postImages.addImage(PostImage.of(this, image));
    }

    public void addAllPostImages(List<Image> images) {
        List<PostImage> postImages = images.stream()
                .map(image -> PostImage.of(this, image))
                .toList();

        this.postImages.addAllImages(postImages);
    }
}
