package com.cos.cercat.messaging.postsearch;

import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.postsearch.PostForSearch;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
public class PostDebeziumDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("certificate_id")
    private Long certificateId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("like_count")
    private Integer likeCount;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("post_type")
    private PostType postType;

    public PostForSearch toDomain() {
        return new PostForSearch(
                id,
                title,
                content,
                certificateId,
                userId,
                likeCount,
                createdAt,
                postType,
                new ArrayList<>()
        );
    }

}
