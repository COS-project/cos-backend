package com.cos.cercat.search.domain;

import com.cos.cercat.post.domain.PostType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private Set<PostCommentDocument> postComments = new HashSet<>();

    public void addComment(PostCommentDocument postCommentDocument) {
        this.postComments.add(postCommentDocument);
    }

    public void removeComment(PostCommentDocument postCommentDocument) {
        this.postComments.remove(postCommentDocument);
    }

    public void update(PostDocument postDocument) {
        this.title = postDocument.getTitle();
        this.content = postDocument.getContent();
        this.certificateId = postDocument.getCertificateId();
        this.userId = postDocument.getUserId();
        this.likeCount = postDocument.getLikeCount();
        this.createdAt = postDocument.getCreatedAt();
        this.postType = postDocument.getPostType();
    }

}
