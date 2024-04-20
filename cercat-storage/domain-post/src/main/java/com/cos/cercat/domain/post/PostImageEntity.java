package com.cos.cercat.domain.post;

import com.cos.cercat.entity.Image;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "post_image")
public class PostImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    private PostEntity postEntity;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "image_id")
    private Image image;


    private PostImageEntity(PostEntity postEntity, Image image) {
        this.postEntity = postEntity;
        this.image = image;
    }

    public static PostImageEntity of(PostEntity postEntity, Image image) {
        return new PostImageEntity(
                postEntity,
                image
        );
    }
}
