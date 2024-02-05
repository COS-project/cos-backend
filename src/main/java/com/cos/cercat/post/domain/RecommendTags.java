package com.cos.cercat.post.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Embeddable
public class RecommendTags {

    @OneToMany(mappedBy = "tipPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecommendTag> recommendTags = new HashSet<>();

    public Set<RecommendTag> getAll() {
        return recommendTags;
    }

    public void addAll(Set<RecommendTag> recommendTags) {
        this.recommendTags.addAll(recommendTags);
    }

}
