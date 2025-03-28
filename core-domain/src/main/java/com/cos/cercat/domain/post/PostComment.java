package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.Ownable;
import com.cos.cercat.domain.user.User;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostComment implements Ownable {
    private Long id;
    private User commenter;
    private Long postId;
    private CommentContent content;
    private DateTime dateTime;

    @Builder.Default
    private List<PostComment> childComments = new ArrayList<>();

    public static PostComment create(User commenter, Post post, CommentContent commentContent) {
        return PostComment.builder()
                .commenter(commenter)
                .postId(post.getId())
                .content(commentContent)
                .dateTime(DateTime.init())
                .build();
    }

    public void addChildren(PostComment child) {
        childComments.add(child);
    }

    public boolean hasParent() {
        return content.parentId() != null;
    }

    @Override
    public User getOwner() {
        return commenter;
    }
}
