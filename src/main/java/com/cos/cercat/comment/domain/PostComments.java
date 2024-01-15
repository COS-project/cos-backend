package com.cos.cercat.comment.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class PostComments {

    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> postComments = new ArrayList<>();

    public void addComment(PostComment comment) {
        this.getPostComments().add(comment);
    }

    public int countComments() {
        return this.postComments.size();
    }

}
