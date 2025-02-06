package com.cos.cercat.database.post.entity.embeddedId;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PostImageId {

    private Long postId;

    private Long imageId;

    public PostImageId(Long postId, Long imageId) {
        this.postId = postId;
        this.imageId = imageId;
    }

}
