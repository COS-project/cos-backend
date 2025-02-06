package com.cos.cercat.search.postsearch.entity;

import com.cos.cercat.domain.postsearch.PostCommentForSearch;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class PostCommentDocument implements Serializable {

    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Keyword)
    private Long postId;

    @Field(type = FieldType.Keyword)
    private Long userId;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Integer)
    private Integer likeCount;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdAt;

    public static PostCommentDocument from(PostCommentForSearch postComment) {
        return PostCommentDocument.builder()
                .id(postComment.id())
                .postId(postComment.postId())
                .userId(postComment.userId())
                .content(postComment.content())
                .likeCount(postComment.likeCount())
                .createdAt(postComment.createdAt())
                .build();
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
