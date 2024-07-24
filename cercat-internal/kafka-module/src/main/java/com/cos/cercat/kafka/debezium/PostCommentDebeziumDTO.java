package com.cos.cercat.kafka.debezium;

import com.cos.cercat.domain.search.PostCommentForSearch;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class PostCommentDebeziumDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("like_count")
    private Integer likeCount;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public void setCreatedAt(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp / 1000);
        ZoneId seoulZoneId = ZoneId.of("UTC");
        ZonedDateTime seoulZonedDateTime = instant.atZone(seoulZoneId);
        this.createdAt = seoulZonedDateTime.toLocalDateTime();
    }

    public PostCommentForSearch toDomain() {
        return new PostCommentForSearch(
                id,
                userId,
                postId,
                content,
                likeCount,
                createdAt
        );
    }

}
