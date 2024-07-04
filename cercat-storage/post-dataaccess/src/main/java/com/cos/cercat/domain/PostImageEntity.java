package com.cos.cercat.domain;

import com.cos.cercat.domain.embeddedId.PostImageId;
import com.cos.cercat.entity.ImageEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "post_image")
public class PostImageEntity implements Persistable<PostImageId> {

    @EmbeddedId
    private PostImageId postImageId = new PostImageId();

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    @OnDelete(action =  CASCADE)
    private PostEntity postEntity;

    @MapsId("imageId")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;

    public static PostImageEntity of(PostEntity postEntity, ImageEntity imageEntity) {
        return new PostImageEntity(
                new PostImageId(postEntity.getId(), imageEntity.getId()),
                postEntity,
                imageEntity
        );
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
