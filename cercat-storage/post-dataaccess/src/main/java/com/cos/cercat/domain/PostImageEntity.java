package com.cos.cercat.domain;

import com.cos.cercat.domain.embeddedId.PostImageId;
import com.cos.cercat.entity.ImageEntity;
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

    @EmbeddedId
    private PostImageId postImageId = new PostImageId();

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    private PostEntity postEntity;

    @MapsId("imageId")
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;

    private PostImageEntity(PostEntity postEntity, ImageEntity imageEntity) {
        this.postEntity = postEntity;
        this.imageEntity = imageEntity;
    }

    public static PostImageEntity of(PostEntity postEntity, ImageEntity imageEntity) {
        return new PostImageEntity(
                new PostImageId(postEntity.getId(), imageEntity.getId()),
                postEntity,
                imageEntity
        );
    }
}
