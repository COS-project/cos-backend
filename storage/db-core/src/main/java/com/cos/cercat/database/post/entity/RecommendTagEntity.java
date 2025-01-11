package com.cos.cercat.database.post.entity;

import com.cos.cercat.domain.post.RecommendTag;
import com.cos.cercat.domain.post.TagType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "recommend_tag")
public class RecommendTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_post_id")
    @OnDelete(action = CASCADE)
    private TipPostEntity tipPost;

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    private String tagName;

    public RecommendTagEntity(TagType tagType, String tagName) {
        this.tagType = tagType;
        this.tagName = tagName;
    }

    public RecommendTagEntity(TipPostEntity tipPost, TagType tagType, String tagName) {
        this.tagType = tagType;
        this.tagName = tagName;
        this.tipPost = tipPost;
    }

    public static RecommendTagEntity of(TipPostEntity tipPost, RecommendTag recommendTag) {
        return new RecommendTagEntity(
                tipPost,
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
