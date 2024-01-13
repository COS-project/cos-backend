package com.cos.cercat.board.domain;

import com.cos.cercat.global.entity.Image;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    private Post post;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;


    private PostImage(Post post, Image image) {
        this.post = post;
        this.image = image;
    }

    public static PostImage of(Post post, Image image) {
        return new PostImage(
                post,
                image
        );
    }
}
