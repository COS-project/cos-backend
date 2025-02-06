package com.cos.cercat.database.post.entity;

import com.cos.cercat.database.common.entity.ImageEntity;
import com.cos.cercat.database.post.entity.embeddedId.PostImageId;
import com.cos.cercat.domain.common.Image;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "post_image")
public class PostImageEntity implements Persistable<PostImageId> {

    @EmbeddedId
    private PostImageId postImageId = new PostImageId();

    @MapsId("imageId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    public static PostImageEntity of(Long postId, Image image) {
        return new PostImageEntity(new PostImageId(postId, image.getId()), ImageEntity.from(image));
    }

    public Image toDomain() {
        return new Image(image.getId(), image.getImageUrl());
    }

    @Override
    public PostImageId getId() {
        return postImageId;
    }

    @Override
    public boolean isNew() {
        return postImageId.getImageId() == null;
    }
}
