package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.post.PostType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostForSearch {
    private final Long id;
    private String title;
    private String content;
    private Long certificateId;
    private Long userId;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private PostType postType;
    private List<PostCommentForSearch> postComments;

    public PostForSearch(Long id,
                         String title,
                         String content,
                         Long certificateId,
                         Long userId,
                         Integer likeCount,
                         LocalDateTime createdAt,
                         PostType postType,
                         List<PostCommentForSearch> postComments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.certificateId = certificateId;
        this.userId = userId;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.postType = postType;
        this.postComments = postComments;
    }

    public void update(PostForSearch postForSearch) {
        this.title = postForSearch.getTitle();
        this.content = postForSearch.getContent();
        this.certificateId = postForSearch.getCertificateId();
        this.userId = postForSearch.getUserId();
        this.likeCount = postForSearch.getLikeCount();
        this.createdAt = postForSearch.getCreatedAt();
        this.postType = postForSearch.getPostType();
        this.postComments = postForSearch.getPostComments();
    }

    public void addComment(PostCommentForSearch postCommentForSearch) {
        this.postComments.add(postCommentForSearch);
    }

    public void removeComment(PostCommentForSearch postComment) {
        this.postComments.remove(postComment);
    }
}
