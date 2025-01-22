package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.Ownable;
import com.cos.cercat.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostComment implements Ownable {
    private Long id;
    private User user;
    private Long postId;
    private CommentContent content;
    private DateTime dateTime;
    private final List<PostComment> childComments;

    public PostComment(Long id, User user, CommentContent content, Long postId, DateTime dateTime) {
        this.id = id;
        this.user = user;
        this.postId = postId;
        this.content = content;
        this.dateTime = dateTime;
        this.childComments = new ArrayList<>();
    }

    public void addChildren(PostComment child) {
        childComments.add(child);
    }

    public boolean hasParent() {
        return content.parentId() != null;
    }
}