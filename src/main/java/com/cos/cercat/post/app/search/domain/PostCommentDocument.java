package com.cos.cercat.post.app.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostCommentDocument implements Serializable {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
