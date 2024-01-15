package com.cos.cercat.comment.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class ChildPostComments {

    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private List<PostComment> childPostComments = new ArrayList<>();

    public void addChildComment(PostComment child) {
        this.getChildPostComments().add(child);
    }
}
