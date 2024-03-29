package com.cos.cercat.domain.comment;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class PostComments {

    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
