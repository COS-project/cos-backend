package com.cos.cercat.domain.post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Embeddable
public class RecommendTags {

    @OneToMany(mappedBy = "tipPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecommendTagEntity> recommendTagEntities = new HashSet<>();

    public Set<RecommendTagEntity> getAll() {
        return recommendTagEntities;
    }

    public void addAll(Set<RecommendTagEntity> recommendTagEntities) {
        this.recommendTagEntities.addAll(recommendTagEntities);
    }

    public void updateTags(Set<RecommendTagEntity> newTags) {
        this.recommendTagEntities.clear();
        this.recommendTagEntities.addAll(newTags);
    }

}
