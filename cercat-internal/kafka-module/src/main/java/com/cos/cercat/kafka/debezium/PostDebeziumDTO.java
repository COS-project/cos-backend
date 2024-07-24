package com.cos.cercat.kafka.debezium;

import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.search.PostForSearch;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
