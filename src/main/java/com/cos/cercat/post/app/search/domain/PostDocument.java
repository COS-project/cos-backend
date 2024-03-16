package com.cos.cercat.post.app.search.domain;

import com.cos.cercat.post.domain.PostType;
import com.google.api.client.util.Lists;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.List;

@Document(indexName = "post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setting(settingPath = "elastic/es-settings.json")
@Mapping(mappingPath = "elastic/es-mappings.json")
public class PostDocument implements Serializable {

    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    private String title;

    private String content;

    private Long certificateId;

    private Long userId;

    private Integer likeCount;

    private Long createdAt;

    private Long modifiedAt;

    private PostType postType;

    @Field(type = FieldType.Nested)
    private List<PostCommentDocument> postComments = Lists.newArrayList();

}
