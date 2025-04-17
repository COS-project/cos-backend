package com.cos.cercat.database.post.entity;

import com.cos.cercat.domain.common.Image;
import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.post.*;

import jakarta.persistence.*;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;

import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Table(
        name = "post",
        indexes = {
                @Index(name = "idx_post_cert_created", columnList = "certificate_id, created_at"),
                @Index(name = "idx_post_type_created", columnList = "post_type, created_at")
        }
)
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;

    protected String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    @OnDelete(action =  CASCADE)
    protected CertificateEntity certificateEntity;

    @Enumerated(EnumType.STRING)
    protected PostType postType;

    protected int commentCount;

    protected int likeCount;

    public Post toDomain(List<Image> images) {
        return switch (postType) {
            case COMMENTARY -> ((CommentaryPostEntity) this).toDomain(images);
            case NORMAL, TIP -> Post.builder()
                    .id(id)
                    .writer(userEntity.toDomain())
                    .certificate(certificateEntity.toDomain())
                    .type(postType)
                    .postContent(new PostContent(title, content))
                    .postImages(images)
                    .dateTime(new DateTime(createdAt, modifiedAt))
                    .commentCount(commentCount)
                    .likeCount(likeCount)
                    .build();
        };
    }

    public Post toDomain(List<Image> images, Set<RecommendTagEntity> recommendTagEntities) {
        return TipPost.builder()
                .id(id)
                .writer(userEntity.toDomain())
                .type(postType)
                .certificate(certificateEntity.toDomain())
                .postContent(new PostContent(title, content))
                .postImages(images)
                .dateTime(new DateTime(createdAt, modifiedAt))
                .recommendTags(recommendTagEntities.stream().map(RecommendTagEntity::toDomain)
                        .collect(Collectors.toSet()))
                .commentCount(commentCount)
                .likeCount(likeCount)
                .build();
    }



    public static PostEntity from(Post post) {
        return switch (post.getType()) {
            case COMMENTARY -> CommentaryPostEntity.from((CommentaryPost) post);
            case NORMAL, TIP -> PostEntity.builder()
                    .id(post.getId())
                    .postType(post.getType())
                    .title(post.getPostContent().title())
                    .content(post.getPostContent().content())
                    .userEntity(UserEntity.from(post.getWriter()))
                    .certificateEntity(CertificateEntity.from(post.getCertificate()))
                    .commentCount(post.getCommentCount())
                    .createdAt(post.getDateTime().createdAt())
                    .modifiedAt(post.getDateTime().modifiedAt())
                    .build();
        };
    }
}
