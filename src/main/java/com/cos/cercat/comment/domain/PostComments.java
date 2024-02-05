package com.cos.cercat.comment.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class PostComments {

    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> postComments = new ArrayList<>();

    public void addComment(PostComment comment) {
        this.postComments.add(comment);
    }

    public List<PostComment> getAll() {
        return this.postComments;
    }


    public int countComments() {
        return this.postComments.size();
    }

}
