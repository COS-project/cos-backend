package com.cos.cercat.domain.post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class PostImages {

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImageEntity> postImageEntities = new ArrayList<>();

    public void addAll(List<PostImageEntity> postImageEntities) {
        this.postImageEntities.addAll(postImageEntities);
    }

    public List<String> getImageUrls() {
        return this.postImageEntities.stream().map(postImage -> postImage.getImage().getImageUrl()).toList();
    }

    public List<PostImageEntity> getAll()  {
        return this.postImageEntities;
    }

    public String getThumbnailImageUrl() {
        return getImageUrls().stream().findFirst().orElse("");
    }

}
