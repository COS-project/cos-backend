package com.cos.cercat.board.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class PostImages {

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    public void addImage(PostImage postImage) {
        this.postImages.add(postImage);
    }

    public void addAllImages(List<PostImage> postImages) {
        this.postImages.addAll(postImages);
    }

    public List<String> getImageUrls() {
        return this.postImages.stream().map(postImage -> postImage.getImage().getImageUrl()).toList();
    }

    public String getThumbnailImageUrl() {
        return getImageUrls().stream().findFirst().orElse("");
    }

}
