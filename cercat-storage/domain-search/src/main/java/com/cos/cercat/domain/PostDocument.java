package com.cos.cercat.domain;

import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.search.PostForSearch;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(indexName = "post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Mapping(mappingPath = "elastic/es-mappings.json")
@Setting(settingPath = "elastic/es-settings.json")
public class PostDocument implements Serializable {

    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private Long certificateId;

    @Field(type = FieldType.Keyword)
    private Long userId;

    @Field(type = FieldType.Integer)
    private Integer likeCount;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdAt;

    private PostType postType;

    @Field(type = FieldType.Nested)
    @Builder.Default
    private List<PostCommentDocument> postComments = new ArrayList<>();

    public static PostDocument from(PostForSearch post) {
        return PostDocument.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .certificateId(post.getCertificateId())
                .userId(post.getUserId())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .postType(post.getPostType())
                .postComments(post.getPostComments().stream().map(PostCommentDocument::from).toList())
                .build();
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
                postComments.stream().map(PostCommentDocument::toDomain).toList()
        );
    }

}
