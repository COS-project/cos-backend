package com.cos.cercat.database.post.entity;

import com.cos.cercat.domain.post.RecommendTag;
import com.cos.cercat.domain.post.TagType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "recommend_tag")
public class RecommendTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    private String tagName;

    public static RecommendTagEntity of(Long postId, RecommendTag recommendTag) {
        return new RecommendTagEntity(
                null,
                postId,
                recommendTag.tagType(),
                recommendTag.tagName()
        );
    }

    public RecommendTag toDomain() {
        return new RecommendTag(
                tagType,
                tagName
        );
    }
}
