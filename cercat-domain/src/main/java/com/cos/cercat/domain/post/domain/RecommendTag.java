package com.cos.cercat.domain.post.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RecommendTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tip_post_id")
    @Setter
    private TipPost tipPost;

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    private String tagName;

    public RecommendTag(TagType tagType, String tagName) {
        this.tagType = tagType;
        this.tagName = tagName;
    }
}
