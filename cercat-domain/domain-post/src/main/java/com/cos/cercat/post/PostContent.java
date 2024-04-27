package com.cos.cercat.post;

import com.cos.cercat.common.domain.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostContent {

    private String title;
    private String content;
    private List<Image> images;

    public PostContent(String title, String content) {
        this.title = title;
        this.content = content;
        this.images = new ArrayList<>();
    }

    public PostContent(String title, String content, List<Image> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public void addImages(List<Image> images) {
        this.images.addAll(images);
    }

    public void update(PostContent newPostContent) {
        this.title = newPostContent.getTitle();
        this.content = newPostContent.getContent();
        addImages(newPostContent.getImages());
    }
}
