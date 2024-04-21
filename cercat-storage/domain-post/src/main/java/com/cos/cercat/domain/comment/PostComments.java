package com.cos.cercat.domain.comment;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class PostComments {

    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "postEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCommentEntity> postCommentEntities = new ArrayList<>();

    public void addComment(PostCommentEntity comment) {
        this.postCommentEntities.add(comment);
    }

    public List<PostCommentEntity> getAll() {
        return this.postCommentEntities;
    }


    public int countComments() {
        return this.postCommentEntities.size();
    }

}
