package com.cos.cercat.post;

import com.cos.cercat.user.Ownable;
import com.cos.cercat.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostComment implements Ownable {
    private final Long id;
    private final User user;
    private final Long postId;
    private final CommentContent content;
    private final DateTime dateTime;
    private final List<PostComment> childComments;

    private int likeCount;

    public PostComment(Long id, User user, CommentContent content, Long postId, int likeCount, DateTime dateTime) {
        this.id = id;
        this.user = user;
        this.postId = postId;
        this.content = content;
        this.likeCount = likeCount;
        this.dateTime = dateTime;
        this.childComments = new ArrayList<>();
    }

    public void addChildren(PostComment child) {
        childComments.add(child);
    }

    public boolean hasParent() {
        return content.parentId() != null;
    }

    public void like() {
        likeCount++;
    }

    public void unLike() {
        likeCount--;
    }

    public boolean isCommentIn(Post post) {
        return this.postId.equals(post.getId());
    }

    @Override
    public boolean isOwner(User user) {
        return this.user.getId().equals(user.getId());
    }
}