package com.cos.cercat.search.dto;

import com.cos.cercat.search.domain.PostCommentDocument;
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
        // 마이크로초를 밀리초로 변환하여 Instant 생성
        Instant instant = Instant.ofEpochMilli(timestamp / 1000);
        // 서울 시간대의 ZoneId를 얻음
        ZoneId seoulZoneId = ZoneId.of("UTC");
        // Instant를 서울 시간대 기준으로 ZonedDateTime으로 변환
        ZonedDateTime seoulZonedDateTime = instant.atZone(seoulZoneId);
        // ZonedDateTime에서 LocalDateTime을 추출
        this.createdAt = seoulZonedDateTime.toLocalDateTime();
    }

    public PostCommentDocument toEntity() {
        return PostCommentDocument.builder()
                .id(id)
                .postId(postId)
                .userId(userId)
                .content(content)
                .likeCount(likeCount)
                .createdAt(createdAt)
                .build();
    }

}
